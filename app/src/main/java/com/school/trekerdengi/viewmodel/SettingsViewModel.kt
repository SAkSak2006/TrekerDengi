package com.school.trekerdengi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school.trekerdengi.data.entity.Goal
import com.school.trekerdengi.repository.GoalRepository  // Добавь GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {
    fun saveGoal(target: Double) {
        viewModelScope.launch {
            val month = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
            val goal = Goal(target = target, month = month)
            goalRepository.insertGoal(goal)
        }
    }
}