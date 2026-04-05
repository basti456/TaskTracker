package com.example.tasktraker.databases.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "TaskName")
    val taskName: String,

    @ColumnInfo(name = "TaskDescription")
    val taskDescription: String,

    @ColumnInfo(name = "Category")
    val category: String,

    @ColumnInfo("DueDate")
    val dueDate: String,

    @ColumnInfo(name = "IsRemindMe")
    val isRemindMe: Int,

    @ColumnInfo(name = "Priority")
    val priority: String,

    @ColumnInfo(name = "FileName")
    val fileName: String = "",

    @ColumnInfo(name = "FileLocation")
    val fileLocation: String = ""
)
