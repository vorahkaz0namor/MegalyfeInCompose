package com.ccink.model.dto

import android.graphics.drawable.Drawable

data class ImageFile(
    val petId: Int = 0,
    val clothesId: Int = 0,
    val fileName: String = "",
    val image: Drawable
)