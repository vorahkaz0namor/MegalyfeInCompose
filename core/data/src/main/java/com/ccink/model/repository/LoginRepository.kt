package com.ccink.model.repository

import com.ccink.model.handleAPICall
import com.ccink.model.model.AuthRequest
import com.ccink.model.model.AuthResponse
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.model.model.ChangePasswordResponse
import com.ccink.model.model.Resource
import com.ccink.model.network.ApiService
import retrofit2.Response

internal class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {
    override suspend fun checkLogin(authRequest: AuthRequest): Resource<AuthResponse> {
        return handleAPICall { apiService.authRequest(authRequest) }
    }

    override suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Resource<ChangePasswordResponse> {
        return handleAPICall { apiService.updatePassword(id, password) }
    }

}

interface LoginRepository {
    suspend fun checkLogin(authRequest: AuthRequest): Resource<AuthResponse>

    suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Resource<ChangePasswordResponse>
}