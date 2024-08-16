package com.example.megalifecompose.ui.main.nav_bar

import com.ccink.resources.R
import com.example.megalifecompose.navigation.BottomNavGraphScreen

data class BottomNavigationItem(
    val label: String = "",
    val iconSelected: Int = R.drawable.ic_home,
    val iconUnselected: Int = R.drawable.home_2,
    val route: String = "",
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Главная",
                iconSelected = R.drawable.ic_home,
                iconUnselected = R.drawable.home_2,
                route = BottomNavGraphScreen.Home.route
            ),
            BottomNavigationItem(
                label = "Рейтинг",
                iconSelected = R.drawable.streamline_interface_favorite_star_reward_rating_rate_social_star_media_favorite_like_stars,
                iconUnselected = R.drawable.ic_favorite_star,
                route = BottomNavGraphScreen.Rating.route
            ),
        )
    }
}