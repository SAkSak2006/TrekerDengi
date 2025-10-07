package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добавить трату", style = MaterialTheme.typography.headlineMedium)
        // Добавь TextField для amount/description, Dropdown для category позже
        Button(onClick = { navController.popBackStack() }) {
            Text("Вернуться")
        }
    }
}