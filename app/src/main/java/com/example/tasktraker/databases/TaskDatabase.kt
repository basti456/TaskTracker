package com.example.tasktraker.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tasktraker.databases.dao.TaskDao
import com.example.tasktraker.databases.entity.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(TaskConverters::class)
abstract class TaskDatabase() : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}