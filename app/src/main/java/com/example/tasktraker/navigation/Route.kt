package com.example.tasktraker.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object TaskList : Route

    @Serializable
    data class TaskDetail(val taskId: Long) : Route
}