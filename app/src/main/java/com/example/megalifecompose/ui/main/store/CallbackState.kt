package com.example.megalifecompose.ui.main.store

import android.graphics.drawable.Drawable
import com.ccink.model.dto.FeedItem

data class CallbackState(
    val confirmError: () -> Unit,
    val clearOpenDialog: () -> Unit,
    val onBackButtonClick: () -> Unit,
    val isItemScreen: () -> Boolean,
    val setItemState: (Class<out FeedItem>, Int, Int) -> ItemButtonsState,
    val onChooserItemClick: (String, Class<out FeedItem>, Int) -> Unit,
    val onSelectedItemClick: (Class<out FeedItem>, Int) -> Unit,
    val setActivePetId: (Int) -> Unit,
    val onBuyItemClick: (Class<out FeedItem>, Int) -> Unit,
    val putOnClothes: (Int?, Int?) -> Unit,
    val getItemFile: (FeedItem) -> Drawable?,
    val getImageRequest: (FeedItem) -> String?,
    val saveItemImage: (FeedItem, Drawable?) -> Unit
)