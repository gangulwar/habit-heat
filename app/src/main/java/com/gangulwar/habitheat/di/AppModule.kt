package com.gangulwar.habitheat.di

import androidx.room.Room
import com.gangulwar.habitheat.data.db.AppDatabase
import com.gangulwar.habitheat.data.repo.HabitRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "habit-tracker-db"
        ).build()
    }

    single { get<AppDatabase>().habitDao() }
    single { get<AppDatabase>().completionDao() }
    single { HabitRepository(get(), get()) }
}
