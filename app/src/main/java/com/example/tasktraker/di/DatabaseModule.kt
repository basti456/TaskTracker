package com.example.tasktraker.di

import androidx.room.Room
import com.example.tasktraker.databases.TaskDatabase
import com.example.tasktraker.repository.TaskRepository
import com.example.tasktraker.viewModels.AddEditTaskViewModel
import com.example.tasktraker.viewModels.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    // 🔹 Create a SINGLE instance of Room database
    // - androidContext() → provided by Koin (Application context)
    // - TaskDatabase::class.java → your Room DB class
    // - "task_database" → database name
    // - .build() → actually creates the database
    // 👉 This will be created once and reused everywhere
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    // 🔹 Provide DAO from the database
    // - get<TaskDatabase>() → fetch the DB instance created above
    // - taskDao() → returns DAO object
    // 👉 DAO is also a singleton and reused everywhere
    single {
        get<TaskDatabase>().taskDao()
    }

    // 🔹 Repository layer
    // - TaskRepository(get()) → inject DAO into repository
    // - get() → resolves TaskDao automatically
    // 👉 Acts as abstraction between ViewModel and DB
    single { TaskRepository(get()) }

    // 🔹 ViewModel for task list screen
    // - viewModel {} → lifecycle-aware (NOT singleton)
    // - get() → injects TaskRepository
    // 👉 New instance created per scope (Activity/NavGraph)
    viewModel { TaskViewModel(get()) }

    // 🔹 ViewModel for add/edit screen
    // - also gets TaskRepository
    // 👉 Separate ViewModel for different screen logic
    viewModel { AddEditTaskViewModel(get()) }
}