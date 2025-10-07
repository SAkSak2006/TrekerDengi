package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.school.trekerdengi.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    var target by remember { mutableStateOf("") }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(title = { Text("Настройки") })
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Лимит на месяц (руб)") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val goalTarget = target.toDoubleOrNull() ?: 0.0
            if (goalTarget > 0) viewModel.saveGoal(goalTarget)
        }) {
            Text("Установить лимит")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
            Text("Ежедневные уведомления")
        }
    }
}