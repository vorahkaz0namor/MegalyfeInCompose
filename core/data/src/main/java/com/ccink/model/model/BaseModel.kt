package com.ccink.model.model

data class BaseModel<T>(
    val status: Int? = null,
    val message: String? = null,
    val data: T? = null,
) : ResponseModel
