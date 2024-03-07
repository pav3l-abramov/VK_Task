package com.example.vktask.navigation

import com.example.vktask.HOME_SCREEN
import com.example.vktask.R
import com.example.vktask.LIST_SCREEN

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object List : BottomBarScreen(
        route = LIST_SCREEN,
        title = "Task",
        icon = R.drawable.baseline_list_24
    )
    object Home : BottomBarScreen(
        route = HOME_SCREEN,
        title = "Home",
        icon = R.drawable.baseline_home_24
    )
}
val screens = listOf(
    BottomBarScreen.List,
    BottomBarScreen.Home,
)