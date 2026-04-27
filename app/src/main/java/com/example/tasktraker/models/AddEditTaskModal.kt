package com.example.tasktraker.models

import androidx.compose.runtime.Immutable

@Immutable
data class AddEditTaskModal(
    val id: Long? = null,
    val taskName: String = "",
    val taskDescription: String = "",
    val category: TaskCategory = TaskCategory.OTHER,
    val dueDate: Long = System.currentTimeMillis(),
    val isRemindMe: Boolean = false,
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.LOW,
    val fileName: String? = null,
    val fileLocation: String? = null
)
