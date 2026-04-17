package com.example.tasktraker.models

import androidx.compose.runtime.Immutable

@Immutable
data class AddEditTaskModal(
    val id: Long? = null,
    val taskName: String = "",
    val taskDescription: String = "",
    val category: TaskCategory = TaskCategory.OTHER,
    val dueDate: Long = System.currentTimeMillis(),
    val isRemindMe: Int = 1,
    val priority: TaskPriority = TaskPriority.LOW,
    val fileName: String = "",
    val fileLocation: String = ""
)
