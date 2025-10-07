package com.school.trekerdengi.repository

import com.school.trekerdengi.data.dao.ExpenseDao
import com.school.trekerdengi.data.entity.Expense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val dao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<Expense>> = dao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense)
    }
}