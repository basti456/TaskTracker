package com.example.tasktraker.databases

import androidx.room.TypeConverter
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import java.util.Locale
import java.util.Locale.getDefault

class TaskConverters {

    @TypeConverter
    fun fromTaskPriority(taskPriority: TaskPriority): String = taskPriority.priority

    @TypeConverter
    fun toTaskPriority(priority: String): TaskPriority = TaskPriority.valueOf(priority.uppercase())

    @TypeConverter
    fun fromTaskCategory(taskCategory: TaskCategory): String = taskCategory.category

    @TypeConverter
    fun toTaskCategory(category: String): TaskCategory = TaskCategory.valueOf(category.uppercase())
}