package com.ccink.model.entity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {
    private val separator = ","

    private fun <T> listToString(list: List<T>?) =
        buildString {
            list?.map { append(it, separator) }
        }

    @TypeConverter
    fun intListToString(list: List<Int>?) =
        listToString(list)

    @TypeConverter
    fun stringToIntList(string: String?) =
        string?.split(separator)
            ?.filter { it.isNotEmpty() }
            ?.map { it.toInt() }

    @TypeConverter
    fun drawableToString(drawable: Drawable): String =
        ByteArrayOutputStream().let { stream ->
            drawable.toBitmap().let {
                it.compress(
                    /* format = */ Bitmap.CompressFormat.PNG,
                    /* quality = */ 100,
                    /* stream = */ stream
                )
                Base64.encodeToString(
                    /* input = */ stream.toByteArray(),
                    /* flags = */ Base64.DEFAULT
                )
            }
        }

    @TypeConverter
    fun stringToDrawable(string: String): Drawable? =
        /**
         * TODO: Is it correct to use there try-catch?
         */
        try {
            Base64.decode(
                /* str = */ string,
                /* flags = */ Base64.DEFAULT
            )
                .let { byteArray ->
                    Drawable.createFromStream(
                        /* is = */ byteArray.inputStream(),
                        /* srcName = */ null
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}