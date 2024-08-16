package com.ccink.model.dto

sealed interface FeedItem {
    val id: Int
    val name: String
    val dressedName: String
    val price: Int
    val description: String
    val image: String
    val fileName: String

    companion object {
        /**
         * Returns true if the item is Clothes and its pets list contains
         * pet with this id.
         */
        fun FeedItem.isSuitableForPet(petId: Int?): Boolean =
            (this as? Clothes)
                ?.petIds
                ?.contains(petId)
                ?: false
    }
}