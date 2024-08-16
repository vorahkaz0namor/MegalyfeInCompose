package com.ccink.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.Pet

@Entity
data class PetEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val dressedName: String,
    val price: Int,
    val description: String,
    val image: String,
    val fileName: String,
    val defaultClothesId: Int,
    val clothesIds: List<Int>
) {
    fun toDto() =
        Pet(
            id = id,
            name = name,
            dressedName = dressedName,
            price = price,
            description = description,
            image = image,
            fileName = fileName,
            defaultClothesId = defaultClothesId,
            clothesIds = clothesIds
        )

    companion object {
        fun fromDto(dtoPet: Pet) =
            PetEntity(
                id = dtoPet.id,
                name = dtoPet.name,
                dressedName = dtoPet.dressedName,
                price = dtoPet.price,
                description = dtoPet.description,
                image = dtoPet.image,
                fileName = dtoPet.fileName,
                defaultClothesId = dtoPet.defaultClothesId,
                clothesIds = dtoPet.clothesIds
            )
    }
}
