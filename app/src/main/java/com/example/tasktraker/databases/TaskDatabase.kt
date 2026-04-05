package com.example.tasktraker.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasktraker.databases.dao.TaskDao
import com.example.tasktraker.databases.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase() : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}

//Dagger-Hilt -> Compile time dependency injection library
//single - Singleton->one instance forever
//factory - New instance every time
//get()
//single<T>
//viewModel