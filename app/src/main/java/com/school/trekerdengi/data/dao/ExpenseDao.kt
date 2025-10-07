package com.school.trekerdengi.data.dao

import androidx.room.*
import com.school.trekerdengi.data.entity.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE date >= :start AND date <= :end GROUP BY category")
    fun getExpensesByCategory(start: Long, end: Long): Flow<List<CategorySum>>

    @Query("SELECT SUM(amount) FROM expenses WHERE date >= :start AND date <= :end")
    fun getTotalByPeriod(start: Long, end: Long): Flow<Double?>
}

data class CategorySum(val category: String, val total: Double)  // Добавь в конец файла