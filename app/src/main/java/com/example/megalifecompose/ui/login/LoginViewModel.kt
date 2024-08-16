package com.example.megalifecompose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.LoginUseCase
import com.ccink.model.config.ConfigService
import com.ccink.model.model.AuthRequest
import com.example.megalifecompose.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Inject
    lateinit var config: ConfigService

    private var authRequest: AuthRequest? = null

    private val _uiState = MutableStateFlow<AuthState?>(null)
    val uiState: StateFlow<AuthState?> = _uiState.asStateFlow()

    init {
        App.getAppComponent().inject(this)
    }

    fun sentEvent(event: AuthEvent) = when (event) {
        is AuthEvent.CheckUser -> {
            conformAuth(event.user)
        }

        is AuthEvent.OnError -> {
            onErrorAction()
        }
    }

    private fun onErrorAction() {
        _uiState.value = null
    }

    private fun conformAuth(value: AuthRequest) {

        authRequest = value

        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            loginUseCase.checkLogin(value).onSuccess {
                it.token?.let { token ->
                    config.saveToken(token)
                }

                it.userId?.let { id ->
                    config.saveId(id.toLong())
                }

                config.saveFirsEntry(it.firstEntry ?: false)

                if (it.firstEntry == true) {
                    _uiState.value = AuthState.ShouldChangePassword
                } else {
                    _uiState.value = AuthState.Success
                }
            }.onFailure {
                _uiState.value = AuthState.Error
            }
        }

    }

}