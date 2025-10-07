package com.school.trekerdengi.data.dao

import androidx.room.*
import com.school.trekerdengi.data.entity.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Query("SELECT * FROM goals WHERE month = :month LIMIT 1")
    fun getGoalForMonth(month: String): Flow<Goal?>
}