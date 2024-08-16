package com.ccink.model.dto

import com.google.gson.annotations.SerializedName

data class PetLink(
    @SerializedName("full_id")
    val fullId: Int,
    @SerializedName("head_id")
    val headId: Int,
    @SerializedName("body_id")
    val bodyId: Int,
    @SerializedName("legs_id")
    val legsId: Int,
    @SerializedName("pet")
    val petId: Int
) {
    override fun toString(): String =
        "PETLINK => petId = $petId, fullId = $fullId, headId = $headId, " +
                "bodyId = $bodyId, legsId = $legsId"
}

fun PetLink.hasWornOn(vararg clothesIds: Int) =
        listOf(headId, bodyId, legsId, fullId)
            .containsAll(clothesIds.asList())
