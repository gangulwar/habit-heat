package com.gangulwar.habitheat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gangulwar.habitheat.data.models.HabitCompletion

@Dao
interface HabitCompletionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: HabitCompletion)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY date ASC")
    suspend fun getCompletionsForHabit(habitId: Long): List<HabitCompletion>

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId AND date = :date")
    suspend fun getCompletionForDay(habitId: Long, date: String): HabitCompletion?

    @Query("SELECT * FROM habit_completions WHERE date = :date")
    suspend fun getAllHabitsCompletedOn(date: String): List<HabitCompletion>
}
