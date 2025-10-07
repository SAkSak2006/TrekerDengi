package com.school.trekerdengi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey val id: Long = 1L,
    val target: Double,
    val current: Double = 0.0,
    val month: String
)