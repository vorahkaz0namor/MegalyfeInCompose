package com.ccink.model.network

import com.ccink.model.model.AuthRequest
import com.ccink.model.model.AuthResponse
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.model.model.ChangePasswordResponse
import retrofit2.Response

internal class ApiServiceImpl(private val api: Api) : ApiService {

    override suspend fun authRequest(authRequest: AuthRequest): Response<AuthResponse> {
        return api.authRequest(authRequest)
    }

    override suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Response<ChangePasswordResponse> {
        return api.updatePassword(id, password)
    }


}