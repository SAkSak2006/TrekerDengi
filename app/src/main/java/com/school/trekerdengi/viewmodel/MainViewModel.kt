package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    private val _todayTotal = MutableStateFlow("0.00")
    val todayTotal: StateFlow<String> = _todayTotal.asStateFlow()

    private val _weekTotal = MutableStateFlow("0.00")
    val weekTotal: StateFlow<String> = _weekTotal.asStateFlow()

    private val _monthTotal = MutableStateFlow("0.00")
    val monthTotal: StateFlow<String> = _monthTotal.asStateFlow()

    private val _progress = MutableStateFlow(0.0)
    val progress: StateFlow<Double> = _progress.asStateFlow()

    init {
        loadTotals()
    }

    private fun loadTotals() {
        viewModelScope.launch {
            // TODO: Calculate from repository
            _todayTotal.value = "500.00"  // Placeholder по скрину
            _weekTotal.value = "500.00"
            _monthTotal.value = "500.00"
            _progress.value = 50.0
        }
    }
}