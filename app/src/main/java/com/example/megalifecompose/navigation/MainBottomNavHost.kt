package com.example.megalifecompose.navigation

import HomeRoute
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.megalifecompose.ui.main.rating.RatingScreen

@Composable
fun MainBottomNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavGraphScreen.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(BottomNavGraphScreen.Home.route) {
            HomeRoute()
        }
        composable(BottomNavGraphScreen.Rating.route) {
            RatingScreen(
                navController
            )
        }
        composable(BottomNavGraphScreen.Store.route) {
            StoreNavHost()
        }
    }
}

sealed class BottomNavGraphScreen(val route: String) {

    companion object {
        const val HOME_SCREEN = "home_screen"
        const val RATING_SCREEN = "rating_screen"
        const val STORE_SCREEN = "store_screen"
    }

    data object Home : BottomNavGraphScreen(HOME_SCREEN)
    data object Rating : BottomNavGraphScreen(RATING_SCREEN)
    data object Store : BottomNavGraphScreen(STORE_SCREEN)
}