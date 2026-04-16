package com.example.tasktraker.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktraker.models.Task
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class TaskUIState {
    object Loading : TaskUIState()
    data class Success(val tasks: List<Task>) : TaskUIState()
    object Empty : TaskUIState()
    data class Error(val message: String) : TaskUIState()
}

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val uiState: StateFlow<TaskUIState> =
        repository.getAllTasks().map { tasks ->
            if (tasks.isEmpty()) {
                TaskUIState.Empty
            } else {
                TaskUIState.Success(tasks)
            }
        }.catch { err ->
            emit(TaskUIState.Error(err.message ?: ""))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = TaskUIState.Loading
        )


}