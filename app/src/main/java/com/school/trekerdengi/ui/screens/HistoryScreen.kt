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
                DismissibleCard(expense = expense, onDelete = { viewModel.deleteExpense(expense) })
            }
        }
    }
}

// Swipe to delete card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissibleCard(expense: Expense, onDelete: () -> Unit) {
    val dismissState = rememberDismissState(
        confirmValueChange = { value ->  // Фикс: DismissValue, not Direction
            if (value == DismissValue.DismissedToEnd) {  // Фикс: DismissValue.DismissedToEnd
                onDelete()
                true
            } else {
                false
            }
        }
    )

    Dismissible(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),  // Фикс: import DismissDirection
        background = {
            Box(  // Фикс: @Composable lambda
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = MaterialTheme.colorScheme.onError)
            }
        }
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {  // Фикс: dismissContent as implicit it { }
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
}