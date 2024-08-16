package com.example.megalifecompose.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ccink.model.dto.Account
import com.example.megalifecompose.ui.main.store.ErrorDialog
import com.example.megalifecompose.ui.main.store.ProgressBar

sealed interface AuthState {
    data object Loading : AuthState
    data object Error : AuthState
    data object Success : AuthState
    data object ShouldChangePassword : AuthState
}


@Composable
fun AuthState?.HandlingLoadState(
    onDismissRequest: () -> Unit,
    navigateToChangePass: () -> Unit,
    navigateToMain: () -> Unit,
    composeAfterSuccess: @Composable () -> Unit
) {
    Column {
        when (this@HandlingLoadState) {
            is AuthState.Loading -> ProgressBar()
            is AuthState.Error -> ErrorDialog(onDismissRequest = onDismissRequest)
            is AuthState.Success -> {
                LaunchedEffect(key1 = true) {
                    navigateToMain()
                }
            }

            is AuthState.ShouldChangePassword -> navigateToChangePass()
            else -> composeAfterSuccess()
        }
    }
}