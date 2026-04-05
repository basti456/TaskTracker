package com.example.tasktraker

import android.app.Application
import com.example.tasktraker.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskTrackerApplication)
            modules(databaseModule)
        }
    }
}