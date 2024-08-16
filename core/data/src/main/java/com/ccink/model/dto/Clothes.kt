package com.ccink.model.dto

import com.google.gson.annotations.SerializedName

data class Clothes(
    override val id: Int,
    override val name: String = "",
    @SerializedName("name_internal")
    override val dressedName: String = "",
    override val price: Int,
    override val description: String = "",
    @SerializedName("download_link")
    override val image: String = "",
    @SerializedName("image")
    override val fileName: String,
    @SerializedName("pets")
    val petIds: List<Int>,
    val category: String = ""
) : FeedItem