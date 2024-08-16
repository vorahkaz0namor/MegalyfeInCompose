package com.ccink.model.network

import com.ccink.model.model.AuthRequest
import com.ccink.model.model.AuthResponse
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.model.model.ChangePasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    @Headers("Content-Type: application/json")
    @POST("api/user/login")
    suspend fun authRequest(@Body authRequest: AuthRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @PUT("api/user/{id}")
    suspend fun updatePassword(
        @Path("id") id: Long,
        @Body password: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

}