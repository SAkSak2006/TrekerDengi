package com.school.trekerdengi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.school.trekerdengi.viewmodel.StatsViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavHostController, viewModel: StatsViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("День", "Неделя", "Месяц")
    val total by viewModel.total.collectAsState()
    val categorySums by viewModel.categorySums.collectAsState()
    val pieEntries by viewModel.pieEntries.collectAsState()
    val lineEntries by viewModel.lineEntries.collectAsState()

    LaunchedEffect(selectedTab) {
        viewModel.loadStats(tabs[selectedTab].lowercase())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Статистика") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
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
                2 -> {  // Месяц — PieChart (по скрину: 100% для категории)
                    if (pieEntries.isNotEmpty()) {
                        AndroidView(factory = { PieChart(it).apply {
                            val dataSet = PieDataSet(pieEntries, "Категории")
                            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                            data = PieData(dataSet)
                            description.isEnabled = false
                            legend.isEnabled = true
                            setUsePercentValues(true)
                            invalidate()
                        } }, modifier = Modifier.fillMaxWidth().height(300.dp))
                    }
                    Text("Всего: $total руб", modifier = Modifier.padding(16.dp))
                }
                1 -> {  // Неделя — LineChart (по скрину: тренд по дням)
                    if (lineEntries.isNotEmpty()) {
                        AndroidView(factory = { LineChart(it).apply {
                            val dataSet = LineDataSet(lineEntries, "Тренд расходов")
                            dataSet.color = ColorTemplate.COLORFUL_COLORS[0]
                            data = LineData(dataSet)
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            description.isEnabled = false
                            invalidate()
                        } }, modifier = Modifier.fillMaxWidth().height(300.dp))
                    }
                    Text("Всего за неделю: $total руб", modifier = Modifier.padding(16.dp))
                }
                else -> {  // День — placeholder
                    Text("Расходы за день: $total руб", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

fun startOfMonth(now: Long): Long = Calendar.getInstance().apply { timeInMillis = now; set(Calendar.DAY_OF_MONTH, 1) }.timeInMillis

fun endOfMonth(now: Long): Long = Calendar.getInstance().apply { timeInMillis = now; set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)) }.timeInMillis