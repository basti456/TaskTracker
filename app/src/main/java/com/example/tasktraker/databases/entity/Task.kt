package com.example.tasktraker.databases.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import com.example.tasktraker.models.Task

@Entity(tableName = "Task")
data class TaskEntity(
    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "TaskName")
    val taskName: String,

    @ColumnInfo(name = "TaskDescription")
    val taskDescription: String,

    @ColumnInfo(name = "Category")
    val category: TaskCategory,

    @ColumnInfo("DueDate")
    val dueDate: Long,

    @ColumnInfo(name = "IsRemindMe")
    val isRemindMe: Boolean,

    @ColumnInfo(name = "Priority")
    val priority: TaskPriority,

    @ColumnInfo(name = "IsCompleted")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "FileName")
    val fileName: String = "",

    @ColumnInfo(name = "FileLocation")
    val fileLocation: String = ""
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    taskName = taskName,
    taskDescription = taskDescription,
    category = category,
    dueDate = dueDate,
    isRemindMe = isRemindMe,
    priority = priority,
    isCompleted = isCompleted,
    fileName = fileName,
    fileLocation = fileLocation
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    taskName = taskName,
    taskDescription = taskDescription,
    category = category,
    dueDate = dueDate,
    isRemindMe = isRemindMe,
    priority = priority,
    isCompleted = isCompleted,
    fileName = fileName?:"",
    fileLocation = fileLocation?:""
)
