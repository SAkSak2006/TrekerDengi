package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _todayTotal = MutableStateFlow(0.0)
    val todayTotal: StateFlow<Double> = _todayTotal.asStateFlow()

    private val _weekTotal = MutableStateFlow(0.0)
    val weekTotal: StateFlow<Double> = _weekTotal.asStateFlow()

    private val _monthTotal = MutableStateFlow(0.0)
    val monthTotal: StateFlow<Double> = _monthTotal.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Сегодня
            val startToday = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val endToday = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 23); set(Calendar.MINUTE, 59); set(Calendar.SECOND, 59); set(Calendar.MILLISECOND, 999)
            }.timeInMillis
            repository.getTotalByPeriod(startToday, endToday).collectLatest { _todayTotal.value = it ?: 0.0 }

            // Неделя
            val startWeek = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }.apply {
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            repository.getTotalByPeriod(startWeek, System.currentTimeMillis()).collectLatest { _weekTotal.value = it ?: 0.0 }

            // Месяц
            val startMonth = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }.apply {
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            repository.getTotalByPeriod(startMonth, System.currentTimeMillis()).collectLatest {
                _monthTotal.value = it ?: 0.0
            }

            // Прогресс (цель - расходы)
            // Добавь GoalRepository позже
            _progress.value = 0f  // Placeholder
        }
    }
}