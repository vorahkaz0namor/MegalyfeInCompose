package com.ccink.model.entity

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.ImageFile

@Entity
data class ImageFileEntity(
    @PrimaryKey
    val fileName: String,
    val petId: Int,
    val clothesId: Int,
    val image: Drawable
) {
    fun toDto() =
        ImageFile(
            fileName = fileName,
            petId = petId,
            clothesId = clothesId,
            image = image
        )

    companion object {
        fun fromDto(dtoPetFile: ImageFile) =
            ImageFileEntity(
                fileName = dtoPetFile.fileName,
                petId = dtoPetFile.petId,
                clothesId = dtoPetFile.clothesId,
                image = dtoPetFile.image
            )
    }
}
