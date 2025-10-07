package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.school.trekerdengi.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val todayTotal by viewModel.todayTotal.collectAsState()
    val weekTotal by viewModel.weekTotal.collectAsState()
    val monthTotal by viewModel.monthTotal.collectAsState()
    val progress by viewModel.progress.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Трекер расходов") },
                actions = {
                    IconButton(onClick = { navController.navigate("add_expense") }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить расход")
                    }
                    IconButton(onClick = { navController.navigate("stats") }) {
                        Icon(Icons.Default.BarChart, contentDescription = "Статистика")
                    }
                    IconButton(onClick = { navController.navigate("history") }) {
                        Icon(Icons.Default.History, contentDescription = "История")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Карточка "День"
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("День", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        Text("${todayTotal} руб", fontSize = 24.sp, fontWeight = FontWeight.Bold)  // Фикс: string interpolation
                    }
                }
            }

            // Карточка "Неделя"
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ArrowDownward, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Неделя", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        Text("${weekTotal} руб", fontSize = 24.sp, fontWeight = FontWeight.Bold)  // Фикс: string interpolation
                    }
                }
            }

            // Карточка "Месяц"
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Wallet, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Месяц", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        Text("${monthTotal} руб", fontSize = 24.sp, fontWeight = FontWeight.Bold)  // Фикс: string interpolation
                    }
                }
            }

            // Прогресс к цели
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Лимит на месяц", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        LinearProgressIndicator(
                            progress = { (progress / 100f).toFloat() },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )
                    }
                }
            }

            // Кнопка "Установить лимит"
            item {
                Button(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Установить лимит на месяц")
                }
            }
        }
    }
}