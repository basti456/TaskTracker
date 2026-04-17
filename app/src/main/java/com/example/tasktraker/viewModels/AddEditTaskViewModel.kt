package com.example.tasktraker.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktraker.models.AddEditTaskModal
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditTaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _addEditTaskUIState = MutableStateFlow(AddEditTaskModal())
    val addEditTaskUIState = _addEditTaskUIState.asStateFlow()

    fun onTaskNameUpdate(taskName: String) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(taskName = taskName)
    }

    fun onTaskDescriptionUpdate(taskDescription: String) {
        _addEditTaskUIState.value =
            _addEditTaskUIState.value.copy(taskDescription = taskDescription)
    }

    fun onTaskCategoryUpdate(taskCategory: TaskCategory) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(category = taskCategory)
    }

    fun onDueDateUpdate(dueDate: Long) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(dueDate = dueDate)
    }

    fun isRemindMeUpdate(isRemindMe: Int) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(isRemindMe = isRemindMe)
    }

    fun onPriorityUpdate(priority: TaskPriority) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(priority = priority)
    }

    fun loadTask(taskId: Long) {
        if (taskId == -1L) return
        viewModelScope.launch {
            repository.getTaskById(taskId)?.let { task ->
                _addEditTaskUIState.value = AddEditTaskModal(
                    id = task.id,
                    taskName = task.taskName,
                    taskDescription = task.taskDescription,
                    category = task.category,
                    dueDate = task.dueDate,
                    isRemindMe = task.isRemindMe,
                    priority = task.priority,
                    fileName = task.fileName,
                    fileLocation = task.fileLocation
                )
            }
        }
    }

    fun saveTask() {
        viewModelScope.launch {
        }
    }
}