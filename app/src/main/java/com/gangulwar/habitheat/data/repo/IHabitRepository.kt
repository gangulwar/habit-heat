package com.gangulwar.habitheat.data.repo

import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion

interface IHabitRepository {
    suspend fun addHabit(name: String, emoji: String?, description: String?)
    suspend fun getAllHabits(): List<Habit>
    suspend fun deleteHabit(habit: Habit)
    suspend fun markHabitCompleted(habitId: Long, date: String, note: String? = null)
    suspend fun isHabitCompleted(habitId: Long, date: String): Boolean
    suspend fun getStatsForHabit(habitId: Long): List<HabitCompletion>
    suspend fun getHabitsCompletedOn(date: String): List<HabitCompletion>
}
