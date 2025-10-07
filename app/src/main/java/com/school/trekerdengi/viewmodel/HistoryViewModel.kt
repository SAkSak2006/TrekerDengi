package com.school.trekerdengi.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    fun getAllExpenses(): Flow<List<Expense>> = repository.getAllExpenses()

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun exportToCSV(expenses: List<Expense>, context: Context) {
        viewModelScope.launch {
            val csvFile = File(context.getExternalFilesDir(null), "expenses.csv")
            FileWriter(csvFile).use { writer ->
                writer.append("Date,Amount,Category,Description\n")
                expenses.forEach { expense ->
                    writer.append("${expense.date},${expense.amount},${expense.category},${expense.description}\n")
                }
            }
            // Snackbar "Экспортировано в ${csvFile.absolutePath}"
        }
    }
}