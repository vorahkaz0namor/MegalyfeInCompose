package com.ccink.model.model

import com.google.gson.annotations.SerializedName

data class AccountPetsModel(
    @SerializedName("full_id")
    val fullId: Any? = null,
    @SerializedName("head_id")
    val headId: Any? = null,
    @SerializedName("body_id")
    val bodyId: Any? = null,
    @SerializedName("legs_id")
    val legsId: Any? = null,
    val pet: PetX
)

data class PetX(
    val description: String,
    val id: Int,
    @SerializedName("download_link")
    val image: String,
    val name: String,
    val price: Int
)