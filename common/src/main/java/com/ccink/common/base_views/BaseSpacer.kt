package com.ccink.common.base_views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun BaseSpacer(height: Dp) {
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(height))
}