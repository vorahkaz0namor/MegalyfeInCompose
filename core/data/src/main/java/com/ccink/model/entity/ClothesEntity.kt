package com.ccink.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.Clothes

@Entity
data class ClothesEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val dressedName: String,
    val price: Int,
    val description: String,
    val image: String,
    val fileName: String,
    val petIds: List<Int>,
    val category: String
) {
    fun toDto() =
        Clothes(
            id = id,
            name = name,
            dressedName = dressedName,
            price = price,
            description = description,
            image = image,
            fileName = fileName,
            petIds = petIds,
            category = category
        )

    companion object {
        fun fromDto(dtoClothes: Clothes) =
            ClothesEntity(
                id = dtoClothes.id,
                name = dtoClothes.name,
                dressedName = dtoClothes.dressedName,
                price = dtoClothes.price,
                description = dtoClothes.description,
                image = dtoClothes.image,
                fileName = dtoClothes.fileName,
                petIds = dtoClothes.petIds,
                category = dtoClothes.category
            )
    }
}