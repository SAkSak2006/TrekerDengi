package com.school.trekerdengi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.school.trekerdengi.ui.screens.AddExpenseScreen
import com.school.trekerdengi.ui.screens.HistoryScreen
import com.school.trekerdengi.ui.screens.MainScreen
import com.school.trekerdengi.ui.screens.SettingsScreen
import com.school.trekerdengi.ui.screens.StatsScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("add_expense") { AddExpenseScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("stats") { StatsScreen(navController) }
        composable("history") { HistoryScreen(navController) }
    }
}