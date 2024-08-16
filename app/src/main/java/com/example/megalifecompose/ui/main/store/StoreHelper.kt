package com.example.megalifecompose.ui.main.store

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ccink.model.dto.Account
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.FeedItemsLists
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.dto.hasWornOn
import com.ccink.resources.*

@Composable
private fun clothesStub() = R.drawable.ic_shirt to MaterialTheme.colors.colorFFC25B

@Composable
private fun petStub() = R.drawable.ic_pet to MaterialTheme.colors.colorFFC25B

@Composable
private fun FeedItem.itemDrawableRes(): Pair<Int, Color> =
    when (this) {
        is Pet ->
            when (id) {
                1 -> R.drawable.pers1 to MaterialTheme.colors.color7CE5E8
                2 -> R.drawable.pers3 to MaterialTheme.colors.colorFFC25B
                3 -> R.drawable.pers2 to MaterialTheme.colors.color5BCECB
                4 -> R.drawable.pers4 to MaterialTheme.colors.color5FA038
                else -> petStub()
            }
        is Clothes ->
            when (id) {
                1 -> R.drawable.christmas_hat to MaterialTheme.colors.colorA5B4FC
                2 -> R.drawable.lynx_clothes_default to MaterialTheme.colors.colorFFC25B
                3 -> R.drawable.rabbit_clothes_defualt to MaterialTheme.colors.color5FA038
                4 -> R.drawable.goat_clothes_default to MaterialTheme.colors.color5BCECB
                6 -> R.drawable.christmas_hat to MaterialTheme.colors.colorA5B4FC
                7 -> R.drawable.lynx_clothes_default to MaterialTheme.colors.colorFFC25B
                8 -> R.drawable.rabbit_clothes_defualt to MaterialTheme.colors.color5FA038
                9 -> R.drawable.goat_clothes_default to MaterialTheme.colors.color5BCECB
                else -> clothesStub()
            }
    }

@Composable
fun FeedItem.getItemImage() = itemDrawableRes().first

@Composable
fun FeedItem.getItemBackground() = itemDrawableRes().second

@Composable
fun List<PetLink>.getItemImage(item: FeedItem): Int =
    (item as? Pet)?.let { thisPet ->
        find { it.petId == thisPet.id }
            ?.petDrawableRes()
    } ?: item.getItemImage()

@Composable
fun PetLink.petDrawableRes(): Int {
    @Composable
    fun petStub(id: Int) = sampleItems().pets[id].itemDrawableRes().first
    return when (petId) {
        1 -> {
            when {
                hasWornOn(1, 2, 3) -> R.drawable.pers1_in_hat_with_t_shirt_and_jeans
                hasWornOn(1, 4) -> R.drawable.pers1_in_hat_and_sweater
                hasWornOn(1, 2) -> R.drawable.pers1_in_hat_and_t_shirt
                hasWornOn(1) -> R.drawable.pers1_in_hat
                else -> petStub(id = petId)
            }
        }
        2 -> {
            when {
                hasWornOn(1, 2, 3) -> R.drawable.pers2_in_hat_with_t_shirt_and_jeans
                hasWornOn(1, 4) -> R.drawable.pers2_in_hat_and_sweater
                hasWornOn(1, 2) -> R.drawable.pers2_in_hat_and_t_shirt
                hasWornOn(1) -> R.drawable.pers2_in_hat
                else -> petStub(id = petId)
            }
        }
        else -> petStub(id = petId)
    }
}

val sampleItems =
    {
        val createdPets =
            buildList {
                add(
                    Pet(
                        id = 2,
                        name = "Марвел",
                        dressedName = "",
                        price = 1000,
                        description = "Марвел - студентка, комсомолка, спортсменка, и просто - красавица",
                        clothesIds = emptyList(),
                        image = "",
                        defaultClothesId = 4,
                        fileName = ""
                    )
                )
            }
        val createdClothes =
            buildList {
                add(
                    Clothes(
                        id = this.size + 1,
                        petIds = listOf(createdPets.first().id),
                        name = "Cap",
                        price = 110,
                        description = "Cap - стильная, яркая и универсальная одежда, идеальная для создания ярких образов",
                        fileName = ""
                    )
                )
                add(
                    Clothes(
                        id = this.size + 1,
                        petIds = listOf(createdPets.first().id),
                        name = "Soft green",
                        price = 380,
                        description = "Soft green - стильная, яркая и универсальная одежда, идеальная для создания ярких образов",
                        fileName = ""
                    )
                )
                add(
                    Clothes(
                        id = this.size + 1,
                        petIds = listOf(createdPets.first().id),
                        name = "Crazy red",
                        price = 280,
                        description = "Crаzy red - стильная, яркая и универсальная одежда, идеальная для создания ярких образов",
                        fileName = ""
                    )
                )
                add(
                    Clothes(
                        id = this.size + 1,
                        petIds = listOf(createdPets.first().id),
                        name = "Blue jeans",
                        price = 360,
                        description = "Blue jeans - стильная, яркая и универсальная одежда, идеальная для создания ярких образов",
                        fileName = ""
                    )
                )
                add(
                    Clothes(
                        id = this.size + 1,
                        petIds = listOf(createdPets.first().id),
                        name = "Foxy lady",
                        price = 1230,
                        description = "Foxy lady - стильная, яркая и универсальная одежда, идеальная для создания ярких образов",
                        fileName = ""
                    )
                )
            }
        FeedItemsLists(
            clothes = createdClothes,
            pets = createdPets.map {
                it.copy(clothesIds = createdClothes.map { it.id })
            }
        )
    }

val sampleAccount =
    Account(
        id = "1",
        balance = 1800,
        clothesIds = listOf(
            sampleItems().clothes.first().id
        ),
        petLinks = listOf(
            PetLink(
                fullId = 0,
                headId = 0,
                bodyId = 0,
                legsId = 0,
                petId = sampleItems().pets.first().id
            )
        )
    )

val callbackStateStub =
    CallbackState(
        confirmError = {},
        clearOpenDialog = {},
        onBackButtonClick = {},
        isItemScreen = { false },
        setItemState = { _, _, _ -> ItemButtonsState() },
        onChooserItemClick = { _, _, _ -> },
        onSelectedItemClick = { _, _ -> },
        setActivePetId = {},
        onBuyItemClick = { _, _ -> },
        putOnClothes = { _, _ -> },
        getItemFile = { null },
        getImageRequest = { "" },
        saveItemImage = { _, _ -> }
    )