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
                2 -> {  // Месяц — PieChart (по скрину)
                    AndroidView(
                        factory = { PieChart(it).apply {
                            data = PieData(PieDataSet(pieEntries, "Категории").apply {
                                colors = ColorTemplate.MATERIAL_COLORS.toList()
                            })
                            description.isEnabled = false
                            legend.isEnabled = true
                            setUsePercentValues(true)
                            invalidate()
                        } },
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                    Text("Всего: $total руб", modifier = Modifier.padding(16.dp))
                }
                1 -> {  // Неделя — LineChart (по скрину)
                    AndroidView(
                        factory = { LineChart(it).apply {
                            data = LineData(LineDataSet(lineEntries, "Тренд").apply {
                                color = ColorTemplate.COLORFUL_COLORS[0]
                            })
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            description.isEnabled = false
                            invalidate()
                        } },
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                    Text("Всего за неделю: $total руб", modifier = Modifier.padding(16.dp))
                }
                else -> Text("Расходы за день: $total руб", modifier = Modifier.padding(16.dp))
            }
        }
    }
}