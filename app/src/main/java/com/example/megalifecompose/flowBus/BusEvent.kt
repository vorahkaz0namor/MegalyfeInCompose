package com.example.megalifecompose.flowBus

import android.graphics.drawable.Drawable
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.PetLink

interface BusEvent

sealed interface FirstPetEvent : BusEvent {
    data object ReadPets: FirstPetEvent
    data class BuyFirstPet(val petId: Int): FirstPetEvent
    data object DismissError: FirstPetEvent
}

sealed interface HomeEvent : BusEvent {
    data object RefreshHome : HomeEvent
}

sealed interface StoreEvent : BusEvent {
    data object LoadData: StoreEvent
    data object ConfirmError: StoreEvent
    data class ShowPetCard(val id: Int): StoreEvent
    data class ShowClothesCard(val id: Int): StoreEvent
    data object ClearOpenDialog: StoreEvent
    data class SelectChooserItem(
        val description: String,
        val itemClass: Class<out FeedItem>,
        val itemId: Int
    ): StoreEvent
    data object SetDefaultChooserItem: StoreEvent
    data object BackToStoreMainScreen: StoreEvent
    data class SetActivePetId(val id: Int): StoreEvent
    data class BuyPet(val id: Int): StoreEvent
    data class BuyClothes(val id: Int): StoreEvent
    data class PutOnSingleClothesOnPet(
        val clothesId: Int,
        val petId: Int
    ): StoreEvent
    data class SaveImageFile(
        val item: FeedItem,
        val image: Drawable?
    ): StoreEvent
    data class GetImageRequest(val petLink: PetLink): StoreEvent
}