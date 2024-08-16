package com.example.megalifecompose.ui.login

import com.ccink.model.model.AuthRequest

sealed interface AuthEvent {
    data class CheckUser(val user: AuthRequest) : AuthEvent
    data object OnError : AuthEvent
}