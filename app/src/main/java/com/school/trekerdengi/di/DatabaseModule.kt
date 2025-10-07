package com.school.trekerdengi.di

import android.content.Context
import com.school.trekerdengi.data.database.AppDatabase
import com.school.trekerdengi.data.dao.ExpenseDao
import com.school.trekerdengi.data.dao.GoalDao  // Добавь import
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getDatabase(context)

    @Provides
    fun provideExpenseDao(db: AppDatabase): ExpenseDao = db.expenseDao()

    @Provides
    fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()  // Добавь
}