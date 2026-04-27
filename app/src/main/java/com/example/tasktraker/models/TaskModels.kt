package com.example.tasktraker.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

enum class TaskPriority(val priority: String, val color: Color) {
    LOW("Low", Color(0xFF1DB129)),
    MEDIUM("Medium", Color(0xFFF4DA16)),
    HIGH("High", Color(0xFFDD2929))
}

enum class TaskCategory(val category: String, val color: Color) {
    WORK("Work", Color(0xFF2196F3)),
    PERSONAL("Personal", Color(0xFF9C27B0)),
    SHOPPING("Shopping", Color(0xFFFF9800)),
    HEALTH("Health", Color(0xFFE91E63)),
    OTHER("Other", Color(0xFF9E9E9E))
}

@Immutable
data class Task(
    val id: Long,
    val taskName: String,
    val taskDescription: String,
    val category: TaskCategory,
    val dueDate: Long,
    val isRemindMe: Boolean,
    val priority: TaskPriority,
    val isCompleted: Boolean,
    val fileName: String?,
    val fileLocation: String?
)

