package com.school.trekerdengi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.school.trekerdengi.ui.screens.AddExpenseScreen  // Добавь import
import com.school.trekerdengi.ui.screens.MainScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("add_expense") { AddExpenseScreen(navController) }  // Добавь маршрут
        // Добавь "stats", "settings" позже
    }
}