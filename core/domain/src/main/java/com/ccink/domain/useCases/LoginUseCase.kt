package com.ccink.domain.useCases

import com.ccink.domain.di.IoDispatcher
import com.ccink.domain.getResponse
import com.ccink.model.model.AuthRequest
import com.ccink.model.model.AuthResponse
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.model.model.ChangePasswordResponse
import com.ccink.model.model.Resource
import com.ccink.model.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class LoginUseCaseImpl(private val repository: LoginRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : LoginUseCase {

    override suspend fun checkLogin(authRequest: AuthRequest): Resource<AuthResponse> {
        return repository.checkLogin(authRequest).getResponse(dispatcher)
    }

    override suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Resource<ChangePasswordResponse> {
        return repository.updatePassword(id, password).getResponse(dispatcher)
    }

}

interface LoginUseCase {
    suspend fun checkLogin(authRequest: AuthRequest): Resource<AuthResponse>

    suspend fun updatePassword(id: Long, password: ChangePasswordRequest): Resource<ChangePasswordResponse>
}

