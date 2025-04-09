package com.gangulwar.habitheat

import android.app.Application
import com.gangulwar.habitheat.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class HabitHeatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HabitHeatApp)
            modules(databaseModule)
        }
    }
}
