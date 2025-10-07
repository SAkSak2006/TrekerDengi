package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    fun addExpense(amount: Double, date: Long, category: String, description: String) {  // Фикс: добавь метод
        viewModelScope.launch {
            val expense = Expense(amount = amount, date = date, category = category, description = description)
            repository.insertExpense(expense)
        }
    }
}