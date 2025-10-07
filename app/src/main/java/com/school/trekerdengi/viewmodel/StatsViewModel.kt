package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.school.trekerdengi.data.dao.CategorySum  // Добавь import из DAO
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    private val _categorySums = MutableStateFlow<List<CategorySum>>(emptyList())  // Теперь тип из DAO
    val categorySums: StateFlow<List<CategorySum>> = _categorySums.asStateFlow()

    private val _pieEntries = MutableStateFlow<List<PieEntry>>(emptyList())
    val pieEntries: StateFlow<List<PieEntry>> = _pieEntries.asStateFlow()

    private val _lineEntries = MutableStateFlow<List<Entry>>(emptyList())
    val lineEntries: StateFlow<List<Entry>> = _lineEntries.asStateFlow()

    fun loadStats(period: String) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val (start, end) = when (period) {
                "day" -> {
                    val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }
                    cal.timeInMillis to now
                }
                "week" -> {
                    val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }
                    cal.timeInMillis to now
                }
                "month" -> {
                    val cal = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }
                    cal.timeInMillis to now
                }
                else -> 0L to now
            }
            repository.getTotalByPeriod(start, end).collectLatest { total ->
                _total.value = total ?: 0.0
            }
            repository.getExpensesByCategory(start, end).collectLatest { sums ->
                _categorySums.value = sums
                _pieEntries.value = sums.map { PieEntry(it.total.toFloat(), it.category) }
                if (period == "week") {
                    val daily = repository.getDailySumsForWeek(start)
                    _lineEntries.value = daily.mapIndexed { index, (day, sum) ->
                        Entry(index.toFloat(), sum.toFloat())
                    }
                }
            }
        }
    }
}