package com.ccink.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationType
import com.ccink.model.dto.Clothes

@Entity
data class AnimationEntity(
    val animationName: String,
    val fileName: String,
    val animationDescription: String,
    @PrimaryKey
    val animationId: Int,
    val animationTypeId: Int?,
    val url: String,
    @Embedded
    val animationType: AnimationType?,
    val clothesId: Int?
) {
    fun toDto(
        clothes: (Int?) -> Clothes?
    ) =
        Animation(
            name = animationName,
            fileName = fileName,
            description = animationDescription,
            id = animationId,
            animationTypeId = animationTypeId,
            url = url,
            animationType = animationType,
            clothes = clothes(clothesId)
        )

    companion object {
        fun fromDto(dtoAnimation: Animation) =
            AnimationEntity(
                animationName = dtoAnimation.name,
                fileName = dtoAnimation.fileName,
                animationDescription = dtoAnimation.description,
                animationId = dtoAnimation.id,
                animationTypeId = dtoAnimation.animationTypeId,
                url = dtoAnimation.url,
                animationType = dtoAnimation.animationType,
                clothesId = dtoAnimation.clothes?.id
            )
    }
}