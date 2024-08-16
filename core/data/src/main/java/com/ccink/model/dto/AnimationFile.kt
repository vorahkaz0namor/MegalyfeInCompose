package com.ccink.model.dto

import android.util.Log

data class AnimationFile(
    val fileName: String,
    val fileAsString: String
)

/**
 * Returns animation file as JsonString from DB
 */
fun List<AnimationFile>.animationFileGetter(
    animations: List<Animation>,
    accountPetLinks: List<PetLink>,
    petId: Int,
    defaultClothesId: Int?
): String? {
    val boughtPet = accountPetLinks.find { it.petId == petId }
    val foundFile =
        animations.getSingleFileName(
            petLink = boughtPet,
            defaultClothesId = defaultClothesId
        )
            ?.let { fileName ->
                find { it.fileName == fileName }
//                    .also {
//                        Log.d("FOUND FILE",
//                            "fileName = ${it?.fileName}")
//                    }
            }
    return foundFile?.fileAsString
}