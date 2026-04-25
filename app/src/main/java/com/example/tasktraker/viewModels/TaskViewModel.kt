package com.example.tasktraker.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktraker.models.Task
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class TaskUIState {
    object Loading : TaskUIState()
    data class Success(val tasks: List<Task>) : TaskUIState()
    object Empty : TaskUIState()
    data class Error(val message: String) : TaskUIState()
}

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<TaskCategory?>(null)

    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    val selectedCategory: StateFlow<TaskCategory?> = _selectedCategory.asStateFlow()


    val uiState: StateFlow<TaskUIState> =
        combine(
            repository.getAllTasks(),
            _selectedCategory,
            _searchQuery
        ) { tasks, category, query ->
            val filteredTask = tasks.filter { task ->
                query.isBlank() || task.taskName.contains(query, ignoreCase = true)
                        || task.taskDescription.contains(query, ignoreCase = true)
            }
                .filter { task ->
                    category == null || task.category == category
                }

            when {
                filteredTask.isEmpty() -> TaskUIState.Empty
                else -> TaskUIState.Success(filteredTask)
            }
        }
            .catch { err ->
                emit(TaskUIState.Error(err.message ?: ""))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3000),
                initialValue = TaskUIState.Loading
            )

    fun onSearchQueryChanged(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    fun onTaskCategoryChanged(taskCategory: TaskCategory?) {
        _selectedCategory.value = taskCategory
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !(task.isCompleted)))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }

    }

}