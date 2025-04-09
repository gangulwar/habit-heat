package com.gangulwar.habitheat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.repo.IHabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HabitViewModel(
    private val repository: IHabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

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

    fun markCompleted(habitId: Long, note: String?) {
        viewModelScope.launch {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.markHabitCompleted(habitId, date, note)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
            loadHabits()
        }
    }
}
