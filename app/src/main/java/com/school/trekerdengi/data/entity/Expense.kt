package com.school.trekerdengi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val date: Long,
    val description: String,
    val category: String,
    val photoPath: String? = null
)