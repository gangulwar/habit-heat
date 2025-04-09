package com.gangulwar.habitheat.di

import androidx.room.Room
import com.gangulwar.habitheat.data.db.AppDatabase
import com.gangulwar.habitheat.data.repo.HabitRepositoryImpl
import com.gangulwar.habitheat.data.repo.IHabitRepository
import com.gangulwar.habitheat.presentation.viewmodel.HabitViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<IHabitRepository> { HabitRepositoryImpl(get(), get()) }
    viewModel { HabitViewModel(get()) }
}
