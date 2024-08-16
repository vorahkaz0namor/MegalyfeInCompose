package com.example.megalifecompose.ui.first_pers_choosen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.ccink.resources.R

class ItemDataState {

    val list = mutableStateListOf(
        PersFirstModel(
            1,
            "Марвел",
            "Творец",
            "Студентка, комсомолка, спортсменка, и просто - красавица",
            R.drawable.pers1,
            Color(0xFFFFC25B)
        ), PersFirstModel(
            2,
            "Лялька",
            "Творец",
            "Творческая и настойчивая девушка, стремящаяся сделать мир лучше через свое искусство.",
            R.drawable.pers2,
            Color(0xFF5194F2)
        ), PersFirstModel(
            3,
            "Лялька",
            "Творец",
            "Творческая и настойчивая девушка, стремящаяся сделать мир лучше через свое искусство.",
            R.drawable.pers3,
            Color(0xFFFFC25B)
        ), PersFirstModel(
            4,
            "Лялька",
            "Творец",
            "Творческая и настойчивая девушка, стремящаяся сделать мир лучше через свое искусство.",
            R.drawable.pers4,
            Color(0xFF5FA038)
        )
    )

    fun onItemSelected(selectedItemData: PersFirstModel) {
        val iterator = list.listIterator()

        while (iterator.hasNext()) {
            val listItem = iterator.next()

            iterator.set(
                if (listItem.id == selectedItemData.id) {
                    listItem.copy(isSelected = true)
                } else {
                    listItem.copy(isSelected = false)
                }
            )
        }
    }

    data class PersFirstModel(
        val id: Int,
        val name: String,
        val title: String,
        val bio: String,
        val pers: Int,
        val backgroundColor: Color,
        var isSelected: Boolean = false,
    )

}