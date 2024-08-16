package com.ccink.model.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    val birthday: String? = null,
    val createdAt: String? = null,
    val email: String? = null,
    @SerializedName("first_entry")
    val firstEntry: Boolean? = null,
    val id: Int? = null,
    val name: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val registrationDate: String? = null,
    val roleId: Int? = null,
    val updatedAt: String? = null,
) : ResponseModel

data class ChangePasswordRequest(
    val password: String
)