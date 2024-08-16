package com.ccink.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.PetLink

@Entity
data class PetLinkEntity(
    val accountId: String,
    @PrimaryKey
    val petId: Int,
    val fullId: Int,
    val headId: Int,
    val bodyId: Int,
    val legsId: Int
) {
    fun toDto() =
        PetLink(
            fullId = fullId,
            headId = headId,
            bodyId = bodyId,
            legsId = legsId,
            petId = petId
        )

    companion object {
        fun fromDto(
            accountId: String,
            dtoPetLink: PetLink
        ) =
            PetLinkEntity(
                accountId = accountId,
                petId = dtoPetLink.petId,
                fullId = dtoPetLink.fullId,
                headId = dtoPetLink.headId,
                bodyId = dtoPetLink.bodyId,
                legsId = dtoPetLink.legsId
            )
    }
}
