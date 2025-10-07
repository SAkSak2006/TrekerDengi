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
fun MainScreen(navController: NavHostController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Treker Dengi", style = MaterialTheme.typography.headlineLarge)
            Button(onClick = { navController.navigate("add_expense") }) {
                Text("Добавить трату")
            }
            // Добавь LazyColumn для списка трат позже
        }
    }
}
