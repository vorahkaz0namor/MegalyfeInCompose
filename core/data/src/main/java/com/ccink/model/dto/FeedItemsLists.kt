package com.ccink.model.dto

data class FeedItemsLists(
    val clothes: List<Clothes> = emptyList(),
    val pets: List<Pet> = emptyList()
)
