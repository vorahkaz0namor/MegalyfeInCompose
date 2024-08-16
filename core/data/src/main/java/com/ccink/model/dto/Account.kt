package com.ccink.model.dto

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Account(
    val id: String,
    val balance: Int,
    @SerializedName("clothes")
    val clothesIds: List<Int>,
    @SerializedName("pet_links")
    val petLinks: List<PetLink>
) {
    override fun toString(): String =
        "accountId = $id, balance = $balance,\n" +
                "petLinks => ${petLinks.map { "\n$it" }},\n" +
                "clothesIds => $clothesIds"

    companion object {
        fun emptyAccount() =
            Account(
                id = "0",
                balance = 0,
                clothesIds = emptyList(),
                petLinks = emptyList()
            )
    }
}