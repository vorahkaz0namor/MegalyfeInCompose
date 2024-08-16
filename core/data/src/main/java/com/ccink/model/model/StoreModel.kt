package com.ccink.model.model

data class ItemRequest(
    val offset: Int = 0,
    val limit: Int = 100
)

data class PutOnSingleClothesRequest(
    val clothesId: Int,
    val petId: Int
)