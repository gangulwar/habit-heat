package com.gangulwar.habitheat.data.repo

import com.gangulwar.habitheat.data.dao.HabitCompletionDao
import com.gangulwar.habitheat.data.dao.HabitDao
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion

class HabitRepository(
    private val habitDao: HabitDao,
    private val completionDao: HabitCompletionDao
) {

    suspend fun addHabit(name: String, emoji: String?) = habitDao.insertHabit(Habit(name = name, emoji = emoji))

    suspend fun getAllHabits() = habitDao.getAllHabits()

    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    suspend fun markHabitCompleted(habitId: Long, date: String) {
        completionDao.insertCompletion(HabitCompletion(habitId = habitId, date = date, isCompleted = true))
    }

    suspend fun isHabitCompleted(habitId: Long, date: String): Boolean {
        return completionDao.getCompletionForDay(habitId, date)?.isCompleted ?: false
    }

    suspend fun getStatsForHabit(habitId: Long): List<HabitCompletion> {
        return completionDao.getCompletionsForHabit(habitId)
    }

    suspend fun getHabitsCompletedOn(date: String): List<HabitCompletion> {
        return completionDao.getAllHabitsCompletedOn(date)
    }
}
