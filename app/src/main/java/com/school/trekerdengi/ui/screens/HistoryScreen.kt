package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.viewmodel.HistoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController, viewModel: HistoryViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var expenses by remember { mutableStateOf(emptyList<Expense>()) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getAllExpenses().collectLatest { list ->
            expenses = list
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("История расходов") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.exportToCSV(expenses, context)
                                snackbarHostState.showSnackbar("Экспортировано в CSV")
                            }
                        }
                    ) {
                        Text("Экспорт CSV")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(expenses) { _, expense ->
                HistoryItem(expense = expense, onDelete = { viewModel.deleteExpense(expense) })
            }
        }
    }
}

// Simple card with delete button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryItem(expense: Expense, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "${expense.amount} руб",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(Date(expense.date))
                    )
                }
                Text(expense.category)
                Text(expense.description)
            }
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}