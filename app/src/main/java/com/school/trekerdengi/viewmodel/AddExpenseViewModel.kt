package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    fun saveExpense(amount: Double, description: String, category: String, photoPath: String? = null) {
        viewModelScope.launch {
            val expense = Expense(
                amount = amount,
                date = System.currentTimeMillis(),
                description = description,
                category = category,
                photoPath = photoPath
            )
            repository.insertExpense(expense)
        }
    }
}