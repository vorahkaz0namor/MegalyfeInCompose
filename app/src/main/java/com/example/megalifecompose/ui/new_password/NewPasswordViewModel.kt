package com.example.megalifecompose.ui.new_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.LoginUseCase
import com.ccink.model.config.ConfigService
import com.ccink.model.model.ChangePasswordRequest
import com.example.megalifecompose.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPasswordViewModel : ViewModel() {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Inject
    lateinit var config: ConfigService

    private val _uiState = MutableStateFlow<NewPasswordState?>(null)
    val uiState: StateFlow<NewPasswordState?> = _uiState.asStateFlow()

    init {
        App.getAppComponent().inject(this)
    }

    fun acceptNewPassword(password: ChangePasswordRequest) {

        viewModelScope.launch {
            loginUseCase.updatePassword(config.getId(), password).onSuccess {
                _uiState.value = NewPasswordState.Success(config.getFirsEntry())
            }.onFailure {
                _uiState.value = NewPasswordState.Error
            }
        }

    }

    sealed class NewPasswordState {
        data class Success(val firstEntry: Boolean) : NewPasswordState()
        data object Error : NewPasswordState()
    }

}