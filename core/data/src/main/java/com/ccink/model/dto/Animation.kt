package com.ccink.model.dto

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Animation(
    val name: String,
    @SerializedName("filename")
    val fileName: String,
    val description: String,
    val id: Int,
    @SerializedName("animation_type_id")
    val animationTypeId: Int?,
    @SerializedName("download_link")
    val url: String,
    @SerializedName("animation_type")
    val animationType: AnimationType?,
    @SerializedName("cloth")
    val clothes: Clothes?
)

/**
 * Returns filenames list for whole animations list
 */
fun List<Animation>.getAllFileNames() = map { it.fileName }

/**
 * Returns animation filename for defined Pet
 */
fun List<Animation>.getSingleFileName(
    petLink: PetLink?,
    defaultClothesId: Int? = null
): String? {
//    Log.d("GET ANIMATION FILENAME",
//        "PETLINK:\n" +
//                "petId = ${petLink?.petId}\n" +
//                "fullId = ${petLink?.fullId}\n" +
//                "defaultClothesId = $defaultClothesId")
    val clothesId = petLink?.fullId ?: defaultClothesId
    val foundAnimation = find {
        it.clothes?.id == clothesId && it.animationTypeId == 1
    }/*.also {
        Log.d("FOUND ANIMATION",
            "clothes.id = ${it?.clothes?.id}\n" +
                    "animationTypeId = ${it?.animationTypeId}")
    }*/
    return foundAnimation?.fileName
}

/**
 * Returns animation filenames list for account Pets
 */
fun List<Animation>.getFileNames(accountPets: List<PetLink>) =
    accountPets.mapNotNull {
        getSingleFileName(petLink = it)
    }