package com.ccink.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccink.model.dto.Account
import com.ccink.model.dto.PetLink

@Entity
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val balance: Int,
    val clothesIds: List<Int>,
    val petIds: List<Int>
) {
    fun toDto(petLinks: List<PetLink>) =
        Account(
            id = id,
            balance = balance,
            clothesIds = clothesIds,
            petLinks = petLinks
        )

    companion object {
        fun fromDto(dtoAccount: Account) =
            AccountEntity(
                id = dtoAccount.id,
                balance = dtoAccount.balance,
                clothesIds = dtoAccount.clothesIds,
                petIds = dtoAccount.petLinks.map { it.petId }
            )
    }
}
