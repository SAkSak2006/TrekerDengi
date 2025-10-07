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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.viewmodel.HistoryViewModel  // Добавь ViewModel для истории
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController, viewModel: HistoryViewModel = hiltViewModel()) {
    var expenses by remember { mutableStateOf(emptyList<Expense>()) }

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
                    Button(onClick = { viewModel.exportToCSV(expenses) }) {  // Экспорт CSV
                        Text("Экспорт CSV")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(expenses) { index, expense ->
                DismissibleCard(expense = expense, onDelete = { viewModel.deleteExpense(expense) })  // Swipe to delete
            }
        }
    }
}

// Swipe to delete card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissibleCard(expense: Expense, onDelete: () -> Unit) {
    var dismissState by rememberDismissState(
        confirmValueChange = { direction ->
            if (direction == DismissDirection.EndToStart) {
                onDelete()
                true
            } else false
        }
    )

    Dismissible(
        state = dismissState,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = MaterialTheme.colorScheme.onError)
            }
        },
        dismissContent = {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
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
            }
        }
    )
}