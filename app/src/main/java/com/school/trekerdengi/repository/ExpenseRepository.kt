package com.school.trekerdengi.repository

import com.school.trekerdengi.data.dao.CategorySum
import com.school.trekerdengi.data.dao.ExpenseDao
import com.school.trekerdengi.data.entity.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val dao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<Expense>> = dao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense)
    }

    fun getExpensesByPeriod(start: Long, end: Long): Flow<List<Expense>> = dao.getExpensesByPeriod(start, end)

    fun getExpensesByCategory(start: Long, end: Long): Flow<List<CategorySum>> = dao.getExpensesByCategory(start, end)

    suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense)
    }

    fun getTotalByPeriod(start: Long, end: Long): Flow<Double?> = dao.getTotalByPeriod(start, end)

    suspend fun getDailySumsForWeek(start: Long): List<Pair<Long, Double>> {
        return dao.getDailySums(start).first().groupBy { it.day }.mapValues { (_, list) -> list.sumOf { it.total } }.toList()
    }
}