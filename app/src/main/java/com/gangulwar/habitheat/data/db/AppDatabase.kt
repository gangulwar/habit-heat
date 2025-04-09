package com.gangulwar.habitheat.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gangulwar.habitheat.data.dao.HabitCompletionDao
import com.gangulwar.habitheat.data.dao.HabitDao
import com.gangulwar.habitheat.data.models.Habit
import com.gangulwar.habitheat.data.models.HabitCompletion

@Database(entities = [Habit::class, HabitCompletion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun completionDao(): HabitCompletionDao
}
