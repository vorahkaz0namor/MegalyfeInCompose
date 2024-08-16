package com.ccink.model.network

import com.ccink.model.model.AuthRequest
import com.ccink.model.model.AuthResponse
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.model.model.ChangePasswordResponse
import retrofit2.Response

interface ApiService {
    suspend fun authRequest(authRequest: AuthRequest): Response<AuthResponse>

    suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Response<ChangePasswordResponse>
}