package com.example.megalifecompose.ui.main.store

data class ItemButtonsState(
    val purchaseButtonVisibility: Boolean = false,
    val putOnButtonVisibility: Boolean = false,
    val purchaseButtonCaption: String = "",
    val putOnButtonCaption: String = "",
    val shouldEnablePurchaseButton: Boolean = false,
    val shouldEnablePutOnButton: Boolean = false
)
