package com.ccink.model.network

import com.ccink.model.dto.Account
import com.ccink.model.dto.AccountStub
import com.ccink.model.dto.Animation
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.model.AccountPetsModel
import com.ccink.model.model.BaseModel
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import retrofit2.Response
import retrofit2.http.*

interface StoreApi {
    @GET("account")
    suspend fun readAccount(): Response<BaseModel<Account>>

    @GET("shop/pets")
    suspend fun readPets(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseModel<List<Pet>>>

    @GET("shop/pets/{pet_id}")
    suspend fun readSinglePet(
        @Path("pet_id") petId: Int
    ): Response<BaseModel<Pet>>

    @POST("shop/pets/{pet_id}/buy")
    suspend fun buyPet(
        @Path("pet_id") petId: Int
    ): Response<BaseModel<AccountStub>>

    @GET("shop/clothes")
    suspend fun readClothes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseModel<List<Clothes>>>

    @GET("shop/clothes/{cloth_id}")
    suspend fun readSingleClothes(
        @Path("cloth_id") clothesId: Int
    ): Response<BaseModel<Clothes>>

    @POST("shop/clothes/{cloth_id}/buy")
    suspend fun buyClothes(
        @Path("cloth_id") clothesId: Int
    ): Response<BaseModel<AccountStub>>

    @Headers("Content-Type: application/json")
    @GET("account/pets")
    suspend fun getAccountPets(): Response<BaseModel<List<AccountPetsModel>>>

    @PATCH("account/clothes/{cloth_id}/puton")
    suspend fun putOnSingleClothesOnPet(
        @Path("cloth_id") clothesId: Int,
        @Query("pet_id") petId: Int
    ): Response<BaseModel<PetLink>>

    @GET("manager/animations/animation")
    suspend fun readAnimations(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseModel<List<Animation>>>

    @GET("files/animation/download")
    suspend fun downloadAnimation(
        @Query("filename") fileName: String
    ): Response<JsonObject>
}