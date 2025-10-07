package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.viewmodel.StatsViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar  // Добавь import

// Функции для дат
fun startOfMonth(now: Long): Long = Calendar.getInstance().apply { timeInMillis = now; set(Calendar.DAY_OF_MONTH, 1) }.timeInMillis

fun endOfMonth(now: Long): Long = Calendar.getInstance().apply { timeInMillis = now; set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)) }.timeInMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavHostController, viewModel: StatsViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("День", "Неделя", "Месяц")
    val context = LocalContext.current
    var expenses by remember { mutableStateOf(emptyList<Expense>()) }
    val total by viewModel.total.collectAsState()
    val categorySums by viewModel.categorySums.collectAsState()
    val pieEntries by viewModel.pieEntries.collectAsState()
    val lineEntries by viewModel.lineEntries.collectAsState()

    LaunchedEffect(selectedTab) {
        viewModel.loadStats(tabs[selectedTab].lowercase())
        val now = System.currentTimeMillis()
        val (start, end) = when (selectedTab) {
            0 -> now - 86400000 to now
            1 -> now - 7 * 86400000 to now
            else -> startOfMonth(now) to endOfMonth(now)  // Теперь resolved
        }
        // Добавь: viewModel.repository.getExpensesByPeriod(start, end).collectLatest { expenses = it }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Статистика") }) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }

            when (selectedTab) {
                2 -> {  // Месяц — PieChart
                    if (pieEntries.isNotEmpty()) {
                        AndroidView(factory = { PieChart(it).apply {
                            val dataSet = PieDataSet(pieEntries, "Категории")
                            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                            data = PieData(dataSet)  // Фикс: PieData(dataSet)
                            description.isEnabled = false
                            legend.isEnabled = true
                            setUsePercentValues(true)
                            invalidate()
                        } }, modifier = Modifier.fillMaxWidth().height(300.dp))
                    }
                    LazyColumn {
                        items(categorySums) { sum ->
                            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                                Text("${sum.category}: ${sum.total} руб", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
                1 -> {  // Неделя — LineChart
                    if (lineEntries.isNotEmpty()) {
                        AndroidView(factory = { LineChart(it).apply {
                            val dataSet = LineDataSet(lineEntries, "Тренд")
                            dataSet.color = ColorTemplate.COLORFUL_COLORS[0]
                            data = LineData(dataSet)  // Фикс: LineData(dataSet)
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            description.isEnabled = false
                            invalidate()
                        } }, modifier = Modifier.fillMaxWidth().height(300.dp))
                    }
                }
                else -> {  // День — список
                    LazyColumn {
                        items(expenses) { expense ->
                            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                                Text("${expense.amount} руб - ${expense.description}", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }

            Button(onClick = { /* exportToCSV() */ }) { Text("Экспорт CSV") }
            Button(onClick = { /* importCSV() */ }) { Text("Импорт CSV") }
        }
    }
}