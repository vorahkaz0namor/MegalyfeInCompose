package com.ccink.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.AnimationFile

@Entity
data class AnimationFileEntity(
    @PrimaryKey
    val fileName: String,
    val fileAsString: String
) {
    fun toDto() =
        AnimationFile(
            fileName = fileName,
            fileAsString = fileAsString
        )

    companion object {
        fun fromDto(dtoAnimationFile: AnimationFile) =
            AnimationFileEntity(
                fileName = dtoAnimationFile.fileName,
                fileAsString = dtoAnimationFile.fileAsString
            )
    }
}