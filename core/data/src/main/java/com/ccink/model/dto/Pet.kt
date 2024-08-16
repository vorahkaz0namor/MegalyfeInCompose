package com.ccink.model.dto

import com.google.gson.annotations.SerializedName

data class Pet(
    override val id: Int,
    override val name: String,
    @SerializedName("name_internal")
    override val dressedName: String,
    override val price: Int,
    override val description: String,
    @SerializedName("download_link")
    override val image: String,
    @SerializedName("image")
    override val fileName: String,
    @SerializedName("default_cloth_id")
    val defaultClothesId: Int,
    @SerializedName("clothes")
    val clothesIds: List<Int>
) : FeedItem {
    override fun toString(): String =
        """
                id = $id
                name = $name
                price = $price
                description = $description
                image = $image
                fileName = $fileName
                clothesIds = "${clothesIds.map { "$it " }}"
            """.trimIndent()
}