package com.example.megalifecompose.ui.main.store

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ccink.model.dto.FeedItem.Companion.isSuitableForPet
import com.ccink.resources.*

@Preview(
    showBackground = true,
    device = "spec:width=360dp,height=592dp"
)
@Composable
fun PreviewStoreMainScreen() {
    MegaLifeComposeTheme {
        val items = sampleItems()
        StoreMainScreen(
            storeState =
                StoreState(
                    clothes = items.clothes,
                    pets = items.pets,
                    activePetId = items.pets.first().id,
                    account = sampleAccount
                ),
            callbackState = callbackStateStub
        )
    }
}

@Composable
fun StoreMainScreen(
    storeState: StoreState,
    callbackState: CallbackState
) {
        Column {
            /**
             * Student balance
             */
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) { StudentBalanceIndicator(storeState.account.balance) }

            /**
             * Characters collection
             */
            Box(
                modifier = Modifier
                    .padding(top = ThirtyDp)
            ) {
                CharactersCollection(
                    storeState = storeState,
                    callbackState = callbackState
                )
            }

            /**
             * Suitable clothes collection
             */
            Box(
                modifier = Modifier
                    .padding(top = FortyThreeDp)
            ) {
                SuitableClothes(
                    storeState = storeState,
                    callbackState = callbackState
                )
            }
        }
}

@Composable
fun CharactersCollection(
    storeState: StoreState,
    callbackState: CallbackState
) {
    RowSection(
        title = CharactersCollection,
        items = storeState.pets,
        storeState = storeState,
        callbackState = callbackState,
        shouldDisplayItemsCounter = !callbackState.isItemScreen(),
    )
}

@Composable
fun CompatibleCharacters(
    storeState: StoreState,
    callbackState: CallbackState
) {
    RowSection(
        title = CompatibleCharacters,
        items = storeState.pets.filter {
            storeState.selectedItem!!.isSuitableForPet(it.id)
        },
        storeState = storeState,
        callbackState = callbackState
    )
}

@Composable
fun SuitableClothes(
    storeState: StoreState,
    callbackState: CallbackState,
    petId: Int? =
        if (callbackState.isItemScreen())
            storeState.selectedItem?.id
        else
            storeState.activePetId
) {
    RowSection(
        title = SuitableClothes,
        items = storeState.clothes.filter {
            it.isSuitableForPet(petId)
        },
        storeState = storeState,
        callbackState = callbackState
    )
}