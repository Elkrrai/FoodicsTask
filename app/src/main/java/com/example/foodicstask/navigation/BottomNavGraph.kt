package com.example.foodicstask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodicstask.tables.presentation.components.TablesScreenRoot

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.Tables.route
    ) {
        composable(BottomNavDestination.Tables.route) {
            TablesScreenRoot()
        }
        composable(BottomNavDestination.Orders.route) {
            // OrdersScreen()
        }
        composable(BottomNavDestination.Menu.route) {
            // MenuScreen()
        }
        composable(BottomNavDestination.Settings.route) {
            //   SettingsScreen()
        }
    }
}
