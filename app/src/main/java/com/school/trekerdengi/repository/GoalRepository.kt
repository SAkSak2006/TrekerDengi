package com.school.trekerdengi.repository

import com.school.trekerdengi.data.dao.GoalDao
import com.school.trekerdengi.data.entity.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val dao: GoalDao
) {
    suspend fun insertGoal(goal: Goal) {
        dao.insertGoal(goal)
    }

    suspend fun updateGoal(goal: Goal) {
        dao.updateGoal(goal)
    }

    fun getGoalForMonth(month: String): Flow<Goal?> = dao.getGoalForMonth(month)
}