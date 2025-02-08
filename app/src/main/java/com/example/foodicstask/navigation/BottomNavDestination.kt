package com.example.foodicstask.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Tables : BottomNavDestination(
        route = "tables",
        label = "Tables",
        icon = Icons.Filled.Restaurant
    )

    object Orders : BottomNavDestination(
        route = "orders",
        label = "Orders",
        icon = Icons.Filled.MenuBook
    )

    object Menu : BottomNavDestination(
        route = "menu",
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu
    )

    object Settings : BottomNavDestination(
        route = "settings",
        label = "Settings",
        icon = Icons.Filled.Settings
    )
}
