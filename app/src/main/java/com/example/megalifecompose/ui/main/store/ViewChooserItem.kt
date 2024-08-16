package com.example.megalifecompose.ui.main.store

import com.ccink.resources.*

data class ViewChooserItem(
    val description: String = "",
    val isSelected: Boolean = false,
    val iconWhenSelected: Int = 0,
    val iconWhenDeselected: Int = 0
) {
    fun getIcon() = if (isSelected) iconWhenSelected else iconWhenDeselected

    fun select() = copy(isSelected = true)

    fun deselect() = copy(isSelected = false)

    fun items(): List<ViewChooserItem> =
        listOf(
            ViewChooserItem(
                description = ItemInformation,
                isSelected = true,
                iconWhenSelected = R.drawable.ic_info,
                iconWhenDeselected = R.drawable.ic_info_deselected
            ),
            ViewChooserItem(
                description = SuitableClothes,
                iconWhenSelected = R.drawable.ic_shirt,
                iconWhenDeselected = R.drawable.ic_shirt_deselected
            ),
            ViewChooserItem(
                description = CompatibleCharacters,
                iconWhenSelected = R.drawable.ic_pet,
                iconWhenDeselected = R.drawable.ic_pet_deselected
            )
        )
}