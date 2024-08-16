package com.ccink.model.network

import android.util.Log
import com.ccink.model.dto.Account
import com.ccink.model.dto.AccountStub
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.model.AccountPetsModel
import com.ccink.model.model.BaseModel
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.PutOnSingleClothesRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

interface StoreApiService {
    suspend fun readAccount(): Response<BaseModel<Account>>
    suspend fun readPets(request: ItemRequest): Response<BaseModel<List<Pet>>>
    suspend fun readSinglePet(petId: Int): Response<BaseModel<Pet>>
    suspend fun buyPet(petId: Int): Response<BaseModel<AccountStub>>
    suspend fun readClothes(request: ItemRequest): Response<BaseModel<List<Clothes>>>
    suspend fun readSingleClothes(clothesId: Int): Response<BaseModel<Clothes>>
    suspend fun buyClothes(clothesId: Int): Response<BaseModel<AccountStub>>
    suspend fun getAccountPets(): Response<BaseModel<List<AccountPetsModel>>>
    suspend fun putOnSingleClothesOnPet(
        putOnRequest: PutOnSingleClothesRequest
    ): Response<BaseModel<PetLink>>
    suspend fun readAnimations(request: ItemRequest): Response<BaseModel<List<Animation>>>
    suspend fun downloadAnimations(
        fileNames: List<String>
    ): Response<BaseModel<List<AnimationFile>>>
}

internal class StoreApiServiceImpl @Inject constructor(
    private val storeApi: StoreApi
) : StoreApiService {
    override suspend fun readAccount(): Response<BaseModel<Account>> =
        storeApi.readAccount()/*.also {
            Log.d("CLOTHES ID IN PETLINKS",
            "${it.body()?.data?.petLinks?.map {
                "pet #${it.petId} in clothes #${it.fullId}\n"
                }}"
            )
        }*/

    override suspend fun readPets(request: ItemRequest): Response<BaseModel<List<Pet>>> =
        storeApi.readPets(
            offset = request.offset,
            limit = request.limit
        )

    override suspend fun readSinglePet(petId: Int): Response<BaseModel<Pet>> =
        storeApi.readSinglePet(petId = petId)

    override suspend fun buyPet(petId: Int): Response<BaseModel<AccountStub>> =
        storeApi.buyPet(petId = petId)

    override suspend fun readClothes(request: ItemRequest): Response<BaseModel<List<Clothes>>> =
        storeApi.readClothes(
            offset = request.offset,
            limit = request.limit
        )

    override suspend fun readSingleClothes(clothesId: Int): Response<BaseModel<Clothes>> =
        storeApi.readSingleClothes(clothesId = clothesId)

    override suspend fun buyClothes(clothesId: Int): Response<BaseModel<AccountStub>> =
        storeApi.buyClothes(clothesId = clothesId)

    override suspend fun getAccountPets(): Response<BaseModel<List<AccountPetsModel>>> =
        storeApi.getAccountPets()

    override suspend fun putOnSingleClothesOnPet(
        putOnRequest: PutOnSingleClothesRequest
    ): Response<BaseModel<PetLink>> =
        storeApi.putOnSingleClothesOnPet(
            clothesId = putOnRequest.clothesId,
            petId = putOnRequest.petId
        )

    override suspend fun readAnimations(request: ItemRequest): Response<BaseModel<List<Animation>>> =
        storeApi.readAnimations(
            offset = request.offset,
            limit = request.limit
        )

    override suspend fun downloadAnimations(
        fileNames: List<String>
    ): Response<BaseModel<List<AnimationFile>>> {
        val animationsFiles =
            fileNames.map {
                AnimationFile(
                    fileName = it,
                    fileAsString = storeApi.downloadAnimation(it)
                        .body().toString()
                )
            }
        // TODO: It's not correct!
        //  This must have replaced to the StoreRepository.
        return Response.success(
            BaseModel(
                status = HTTP_OK,
                data = animationsFiles
            )
        )
    }
}