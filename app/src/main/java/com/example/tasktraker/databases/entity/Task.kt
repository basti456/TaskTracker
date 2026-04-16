package com.example.tasktraker.databases.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority


@Entity(tableName = "Task")
data class Task(
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
    val dueDate: String,

    @ColumnInfo(name = "IsRemindMe")
    val isRemindMe: Int,

    @ColumnInfo(name = "Priority")
    val priority: TaskPriority,

    @ColumnInfo(name = "FileName")
    val fileName: String = "",

    @ColumnInfo(name = "FileLocation")
    val fileLocation: String = ""
)
