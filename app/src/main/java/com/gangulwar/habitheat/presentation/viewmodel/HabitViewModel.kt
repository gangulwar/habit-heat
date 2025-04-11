package com.gangulwar.habitheat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion
import com.gangulwar.habitheat.data.models.ProgressStatus
import com.gangulwar.habitheat.data.repo.IHabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitViewModel(
    val repository: IHabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    private val _habitCompletionsMap = MutableStateFlow<Map<Long, List<HabitCompletion>>>(emptyMap())
    val habitCompletionsMap: StateFlow<Map<Long, List<HabitCompletion>>> = _habitCompletionsMap

    fun getStatsForHabit(habitId: Long) {
        viewModelScope.launch {
            val completions = repository.getStatsForHabit(habitId)
            _habitCompletionsMap.update { currentMap ->
                currentMap.toMutableMap().apply {
                    this[habitId] = completions
                }
            }
        }
    }

    init {
        loadHabits()
    }

    fun loadHabits() {
        viewModelScope.launch {
            _habits.value = repository.getAllHabits()
        }
    }

    fun addHabit(name: String, emoji: String?, description: String?) {
        viewModelScope.launch {
            repository.addHabit(name, emoji, description)
            loadHabits()
        }
    }

    fun markCompletedAndRefresh(habitId: Long, note: String?, date: String, progressStatus: ProgressStatus) {
        viewModelScope.launch {
            repository.markHabitCompleted(habitId, date, note, progressStatus)
            getStatsForHabit(habitId)
        }
    }

    fun getStatsForHabit(habitId: Long, onResult: (List<HabitCompletion>) -> Unit) {
        viewModelScope.launch {
            val stats = repository.getStatsForHabit(habitId)
            onResult(stats)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
            loadHabits()
        }
    }
}
