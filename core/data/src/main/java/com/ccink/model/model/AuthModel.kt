package com.ccink.model.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String? = null,
    @SerializedName("first_entry")
    val firstEntry: Boolean? = null,
    val userId: Int? = null,
): ResponseModel

data class AuthRequest(
    val email: String? = null,
    val password: String? = null,
)