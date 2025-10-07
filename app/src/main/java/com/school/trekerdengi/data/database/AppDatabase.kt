package com.school.trekerdengi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.school.trekerdengi.data.dao.ExpenseDao
import com.school.trekerdengi.data.dao.GoalDao
import com.school.trekerdengi.data.entity.Expense
import com.school.trekerdengi.data.entity.Goal

@Database(
    entities = [Expense::class, Goal::class],  // Добавь Goal
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun goalDao(): GoalDao  // Добавь

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "treker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}