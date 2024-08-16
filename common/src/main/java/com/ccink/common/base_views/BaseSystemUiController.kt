package com.ccink.common.base_views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BaseSystemUiController(systemUiController: SystemUiController, navigationBarColor: Color = Color.Transparent, statusBarColor: Color = Color.Transparent, darkIcons: Boolean = true) {

    DisposableEffect(systemUiController) {
        systemUiController.setNavigationBarColor(navigationBarColor, darkIcons)
        systemUiController.setStatusBarColor(statusBarColor, darkIcons)
        onDispose {}
    }

}