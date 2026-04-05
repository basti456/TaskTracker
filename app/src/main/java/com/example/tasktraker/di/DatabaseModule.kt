package com.example.tasktraker.di

import androidx.room.Room
import com.example.tasktraker.databases.TaskDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    single {
        get<TaskDatabase>().taskDao()
    }
}