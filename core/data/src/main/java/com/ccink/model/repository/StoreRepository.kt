package com.ccink.model.repository

import android.util.Log
import androidx.room.withTransaction
import com.ccink.model.Constants.Companion.createDressedPetImageRequest
import com.ccink.model.dao.StoreDao
import com.ccink.model.database.AppDb
import com.ccink.model.dto.Account
import com.ccink.model.dto.AccountStub
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.ImageFile
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.entity.AccountEntity
import com.ccink.model.entity.AnimationEntity
import com.ccink.model.entity.AnimationFileEntity
import com.ccink.model.entity.ClothesEntity
import com.ccink.model.entity.PetEntity
import com.ccink.model.entity.ImageFileEntity
import com.ccink.model.entity.PetLinkEntity
import com.ccink.model.handleAPICall
import com.ccink.model.handleDAOCall
import com.ccink.model.handleWithDao
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.PutOnSingleClothesRequest
import com.ccink.model.model.Resource
import com.ccink.model.model.BaseModel
import com.ccink.model.network.StoreApiService
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

interface StoreRepository {
    suspend fun readAccount(): Resource<BaseModel<Account>>
    suspend fun readPets(request: ItemRequest): Resource<BaseModel<List<Pet>>>
    suspend fun readSinglePet(petId: Int): Resource<BaseModel<Pet>>
    suspend fun buyPet(petId: Int): Resource<BaseModel<AccountStub>>
    suspend fun readClothes(request: ItemRequest): Resource<BaseModel<List<Clothes>>>
    suspend fun readSingleClothes(clothesId: Int): Resource<BaseModel<Clothes>>
    suspend fun buyClothes(clothesId: Int): Resource<BaseModel<AccountStub>>
    suspend fun putOnSingleClothesOnPet(
        putOnRequest: PutOnSingleClothesRequest
    ): Resource<BaseModel<PetLink>>
    suspend fun readAnimations(request: ItemRequest): Resource<BaseModel<List<Animation>>>
    suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>>
    suspend fun saveImageFile(
        imageFile: ImageFile
    ): Resource<BaseModel<Boolean>>
    suspend fun getImageFiles(): Resource<BaseModel<List<ImageFile>>>
    suspend fun getImageRequest(
        petId: Int,
        clothesId: Int?
    ): Resource<BaseModel<String>>
}

internal class StoreRepositoryImpl @Inject constructor(
    private val storeApiService: StoreApiService,
    private val appDb: AppDb,
    private val storeDao: StoreDao
): StoreRepository {
    override suspend fun readAccount(): Resource<BaseModel<Account>> =
        handleDAOCall {
            var accountId: String = Account.emptyAccount().id
            handleAPICall {
                storeApiService.readAccount().handleWithDao { account ->
                    accountId = account.id
                    appDb.withTransaction {
                        storeDao.saveAccount(
                            AccountEntity.fromDto(account)
                        )
                        storeDao.deletePetLinks(
                            storeDao.getPetLinks(account.id).filter {
                                !account.petLinks
                                    .map { it.petId }
                                    .contains(it.petId)
                            }
                        )
                        storeDao.savePetLinks(
                            account.petLinks.map {
                                PetLinkEntity.fromDto(account.id, it)
                            }
                        )
                    }
                }
            }
                .let {
                    storeDao.getAccount(accountId).toDto(
                        storeDao.getPetLinks(accountId)
                            .map { it.toDto() }
                    )/*.also {
                        Log.d("ACCOUNT GOT FROM DB",
                            it.toString())
                    }*/
                }
        }

    override suspend fun readPets(request: ItemRequest): Resource<BaseModel<List<Pet>>> =
        handleDAOCall {
            val getDaoPets = suspend { storeDao.getAllPets().map(PetEntity::toDto) }
            handleAPICall {
                storeApiService.readPets(request).handleWithDao { petsToUpdate ->
                        storeDao.savePets(
                            petsToUpdate
                                .filterNot { getDaoPets().contains(it) }
                                .map(PetEntity.Companion::fromDto))
                    }
            }
            getDaoPets()
        }

    override suspend fun readSinglePet(petId: Int): Resource<BaseModel<Pet>> =
        handleDAOCall {
            var singlePet = storeDao.getSinglePet(petId)?.toDto()
            singlePet ?: handleAPICall {
                    storeApiService.readSinglePet(petId).handleWithDao {
                        storeDao.savePets(listOf(PetEntity.fromDto(it)))
                        singlePet = it
                    }
                }
            singlePet
        }

    override suspend fun buyPet(petId: Int): Resource<BaseModel<AccountStub>> =
        handleAPICall { storeApiService.buyPet(petId) }

    override suspend fun readClothes(
        request: ItemRequest
    ): Resource<BaseModel<List<Clothes>>> =
        handleDAOCall {
            val getDaoClothes = suspend { storeDao.getAllClothes().map(ClothesEntity::toDto) }
            handleAPICall {
                storeApiService.readClothes(request).handleWithDao { clothesToUpdate ->
                        storeDao.saveClothes(
                            clothesToUpdate
                                .filterNot { getDaoClothes().contains(it) }
                                .map(ClothesEntity.Companion::fromDto)
                        )
                    }
            }
            getDaoClothes()
        }

    override suspend fun readSingleClothes(clothesId: Int): Resource<BaseModel<Clothes>> =
        handleDAOCall {
            var singleClothes = storeDao.getSingleClothes(clothesId)?.toDto()
            singleClothes ?: handleAPICall {
                storeApiService.readSingleClothes(clothesId).handleWithDao {
                    storeDao.saveClothes(listOf(ClothesEntity.fromDto(it)))
                    singleClothes = it
                }
            }
            singleClothes
        }

    override suspend fun buyClothes(clothesId: Int): Resource<BaseModel<AccountStub>> =
        handleAPICall { storeApiService.buyClothes(clothesId) }

    override suspend fun putOnSingleClothesOnPet(
        putOnRequest: PutOnSingleClothesRequest
    ): Resource<BaseModel<PetLink>> =
        handleAPICall { storeApiService.putOnSingleClothesOnPet(putOnRequest) }

    override suspend fun readAnimations(
        request: ItemRequest
    ): Resource<BaseModel<List<Animation>>> =
        handleDAOCall {
            suspend fun getDaoAnimations(clothes: List<Clothes>): List<Animation> =
                storeDao.getAllAnimations().map { animationEntity ->
                    animationEntity.toDto { clothesId ->
                        clothes.find { it.id == clothesId }
                    }
                }
            storeDao.getAllClothes().map(ClothesEntity::toDto)
                .let { clothes ->
                    handleAPICall {
                        storeApiService.readAnimations(request)
                            .handleWithDao { animationsToUpdate ->
//                                Log.d("ANIMATIONS FROM SERVER",
//                                    "${animationsToUpdate
//                                        .map { "clothesId => ${it.clothes?.id}" }}")
                                storeDao.saveAnimations(
                                    animationsToUpdate
                                        .filterNot {
                                            getDaoAnimations(clothes).contains(it)
                                        }
                                        .map(AnimationEntity::fromDto)
                                )
                            }
                    }
                        .let { getDaoAnimations(clothes) }
                }
        }

    override suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>> =
        handleDAOCall {
            val fileNamesToUpdate = fileNames.filter {
                !storeDao.getAllAnimationFilenames().contains(it)
            }
            if (fileNamesToUpdate.isNotEmpty()) {
                handleAPICall {
                    storeApiService.downloadAnimations(fileNamesToUpdate)
                        .handleWithDao {
                            storeDao.saveAnimationsFiles(
                                it.map(AnimationFileEntity::fromDto)
                            )
                        }
                }
            }
            fileNames.map {
                storeDao.getAnimationFile(it).toDto()
            }
        }

    override suspend fun saveImageFile(
        imageFile: ImageFile
    ): Resource<BaseModel<Boolean>> =
        handleDAOCall {
            storeDao.saveImageFile(
                ImageFileEntity.fromDto(imageFile)
            )
            true
        }

    override suspend fun getImageFiles(): Resource<BaseModel<List<ImageFile>>> =
        handleDAOCall { storeDao.getImageFiles().map { it.toDto() } }

    override suspend fun getImageRequest(
        petId: Int,
        clothesId: Int?
    ): Resource<BaseModel<String>> =
        Resource.Success(
            BaseModel(
                status = HTTP_OK,
                data = createDressedPetImageRequest(
                    petId = petId,
                    clothesId = clothesId
                )
            )
        )
}