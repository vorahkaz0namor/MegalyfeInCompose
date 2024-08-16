package com.ccink.domain.useCases

import com.ccink.domain.di.IoDispatcher
import com.ccink.domain.getResponse
import com.ccink.model.dto.Account
import com.ccink.model.dto.AccountStub
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.ImageFile
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.model.BaseModel
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.PutOnSingleClothesRequest
import com.ccink.model.model.Resource
import com.ccink.model.repository.StoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface StoreUseCase {
    suspend fun readAccount(): Resource<BaseModel<Account>>
    suspend fun readPets(): Resource<BaseModel<List<Pet>>>
    suspend fun readSinglePet(petId: Int): Resource<BaseModel<Pet>>
    suspend fun buyPet(petId: Int): Resource<BaseModel<AccountStub>>
    suspend fun readClothes(): Resource<BaseModel<List<Clothes>>>
    suspend fun readSingleClothes(clothesId: Int): Resource<BaseModel<Clothes>>
    suspend fun buyClothes(clothesId: Int): Resource<BaseModel<AccountStub>>
    suspend fun putOnSingleClothesOnPet(
        clothesId: Int, petId: Int
    ): Resource<BaseModel<PetLink>>
    suspend fun readAnimations(): Resource<BaseModel<List<Animation>>>
    suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>>
    suspend fun saveImageFile(imageFile: ImageFile): Resource<BaseModel<Boolean>>
    suspend fun getImageFiles(): Resource<BaseModel<List<ImageFile>>>
    suspend fun getImageRequest(
        petId: Int,
        clothesId: Int?
    ): Resource<BaseModel<String>>
}

internal class StoreUseCaseImpl @Inject constructor(
    private val repository: StoreRepository,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): StoreUseCase {
    override suspend fun readAccount(): Resource<BaseModel<Account>> =
        repository.readAccount().getResponse(dispatcher)

    override suspend fun readPets(): Resource<BaseModel<List<Pet>>> =
        repository.readPets(request = ItemRequest()).getResponse(dispatcher)

    override suspend fun readSinglePet(petId: Int): Resource<BaseModel<Pet>> =
        repository.readSinglePet(petId = petId).getResponse(dispatcher)

    override suspend fun buyPet(petId: Int): Resource<BaseModel<AccountStub>> =
        repository.buyPet(petId = petId).getResponse(dispatcher)

    override suspend fun readClothes(): Resource<BaseModel<List<Clothes>>> =
        repository.readClothes(request = ItemRequest()).getResponse(dispatcher)

    override suspend fun readSingleClothes(clothesId: Int): Resource<BaseModel<Clothes>> =
        repository.readSingleClothes(clothesId = clothesId).getResponse(dispatcher)

    override suspend fun buyClothes(clothesId: Int): Resource<BaseModel<AccountStub>> =
        repository.buyClothes(clothesId = clothesId).getResponse(dispatcher)

    override suspend fun putOnSingleClothesOnPet(
        clothesId: Int,
        petId: Int
    ): Resource<BaseModel<PetLink>> =
        repository.putOnSingleClothesOnPet(
            putOnRequest = PutOnSingleClothesRequest(
                clothesId = clothesId,
                petId = petId
            )
        ).getResponse(dispatcher)

    override suspend fun readAnimations(): Resource<BaseModel<List<Animation>>> =
        repository.readAnimations(request = ItemRequest()).getResponse(dispatcher)

    override suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>> =
        repository.downloadAnimations(fileNames = fileNames).getResponse(dispatcher)

    override suspend fun saveImageFile(
        imageFile: ImageFile
    ): Resource<BaseModel<Boolean>> =
        repository.saveImageFile(imageFile = imageFile).getResponse(dispatcher)

    override suspend fun getImageFiles(): Resource<BaseModel<List<ImageFile>>> =
        repository.getImageFiles().getResponse(dispatcher)

    override suspend fun getImageRequest(
        petId: Int,
        clothesId: Int?
    ): Resource<BaseModel<String>> =
        repository.getImageRequest(
            petId = petId,
            clothesId = clothesId
        ).getResponse(dispatcher)
}