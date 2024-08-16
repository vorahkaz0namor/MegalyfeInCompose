package com.example.megalifecompose.ui.main.store

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.graphics.drawable.toBitmap
import com.ccink.model.dto.Account
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.ImageFile
import com.ccink.model.dto.MegalifeImageRequest
import com.ccink.model.dto.Pet
import com.ccink.model.dto.animationFileGetter
import com.ccink.resources.ThirtyDp
import com.ccink.resources.ThirtySixDp

data class StoreState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val handlerMessage: String? = null,
    val openDialog: Boolean = false,
    val clothes: List<Clothes> = emptyList(),
    val pets: List<Pet> = emptyList(),
    val animations: List<Animation> = emptyList(),
    val animationFiles: List<AnimationFile> = emptyList(),
    val imageFiles: List<ImageFile> = emptyList(),
    val imageRequests: List<MegalifeImageRequest> = emptyList(),
    val viewChooserItems: List<ViewChooserItem> = ViewChooserItem().items(),
    val selectedItem: FeedItem? = null,
    val activePetId: Int = 1,
    val account: Account = Account.emptyAccount(),
    val boughtItem: FeedItem? = null
) {
    override fun toString(): String =
        "loading = $loading\n" +
                "error = $error\n" +
        "openDialog = $openDialog\n" +
                "clothes = ${clothes.size}\n" +
                "pets = ${pets.size}\n" +
                "selectedItem = ${selectedItem?.javaClass}\n" +
                "activePetId = $activePetId\n" +
                "boughtItem = ${boughtItem?.javaClass}"
}

fun StoreState.loading() = copy(
    loading = true,
    error = false
)

fun StoreState.error(message: String?) = copy(
    loading = false,
    error = true,
    handlerMessage = message
)

fun StoreState.display() = copy(
    loading = false,
    error = false
)

fun StoreState.addActivePetIdToPutOnClothes(
    putOnClothes: (Int?, Int?) -> Unit
): (Int?) -> Unit = { clothesId: Int? ->
    putOnClothes(activePetId, clothesId)
}

fun StoreState.addSelectedPetIdToPutOnClothes(
    putOnClothes: (Int?, Int?) -> Unit
): (Int?) -> Unit = { clothesId: Int? ->
    putOnClothes(selectedItem?.id, clothesId)
}

fun StoreState.getItemFile(item: FeedItem): Drawable? {
//    Log.d("LET'S GET ITEM FILE",
//        "for item #${item.id} with fileName = ${item.fileName}")
    val boughtPet = account.petLinks
        .find { it.petId == (item as? Pet)?.id }
    val predicate = { file: ImageFile ->
        if (boughtPet != null)
            file.petId == boughtPet.petId &&
                    file.clothesId == boughtPet.fullId
        else
            file.fileName == item.fileName
    }
    return imageFiles.find { predicate(it) }.let {
//            if (it != null)
//                Log.d("FILE WAS FOUND",
//                    "for item #${item.id} with fileName = ${it.fileName}\n" +
//                            "size of the file = " +
//                            "${it.image.toBitmap().byteCount} bytes")
//            else
//                Log.d("ATTENTION!", "Unfortunately, file not found")
            it?.image
    }
}

fun StoreState.getAnimationFile(petId: Int): String? =
    animationFiles.animationFileGetter(
        animations = animations,
        accountPetLinks = account.petLinks,
        petId = petId,
        defaultClothesId = pets.find { it.id == petId }?.defaultClothesId
    )

@Composable
fun StoreState.HandlingLoadState(
    callbackState: CallbackState,
    putOnClothes: (Int?) -> Unit = {},
    composeAfterSuccess: @Composable () -> Unit
) {
        when {
            loading -> ProgressBar()
            error -> ErrorDialog(
                onDismissRequest = callbackState.confirmError,
                message = handlerMessage
            )
            else -> {
                if (openDialog && boughtItem != null) {
                    AfterPurchaseDialog(
                        callbackState = callbackState,
                        putOnClothes = putOnClothes,
                        item = boughtItem,
                        wearOnButtonIsVisible = boughtItem
                            .let { it is Clothes && it != selectedItem }
                    )
                }
                Column(
                    /**
                     * Padding for content to not overlaps by FAB
                     */
                    modifier = Modifier.padding(bottom = ThirtySixDp)
                ) { composeAfterSuccess() }
            }
        }
}