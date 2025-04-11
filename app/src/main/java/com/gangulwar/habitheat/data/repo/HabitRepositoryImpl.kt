package com.gangulwar.habitheat.data.repo

import com.gangulwar.habitheat.data.dao.HabitCompletionDao
import com.gangulwar.habitheat.data.dao.HabitDao
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion
import com.gangulwar.habitheat.data.models.ProgressStatus

class HabitRepositoryImpl(
    private val habitDao: HabitDao,
    private val completionDao: HabitCompletionDao
) : IHabitRepository {

    override suspend fun addHabit(name: String, emoji: String?, description: String?) {
        habitDao.insertHabit(Habit(name = name, emoji = emoji, description = description))
    }

    override suspend fun getAllHabits(): List<Habit> {
        return habitDao.getAllHabits()
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    override suspend fun markHabitCompleted(habitId: Long, date: String, note: String?, progressStatus: ProgressStatus) {
        completionDao.insertCompletion(
            HabitCompletion(
                habitId = habitId,
                date = date,
                note = note,
                isCompleted = true,
                progressStatus = progressStatus.status
            )
        )
    }

    override suspend fun isHabitCompleted(habitId: Long, date: String): Boolean {
        return completionDao.getCompletionForDay(habitId, date)?.isCompleted ?: false
    }

    override suspend fun getStatsForHabit(habitId: Long): List<HabitCompletion> {
        return completionDao.getCompletionsForHabit(habitId)
    }

    override suspend fun getHabitsCompletedOn(date: String): List<HabitCompletion> {
        return completionDao.getAllHabitsCompletedOn(date)
    }
}