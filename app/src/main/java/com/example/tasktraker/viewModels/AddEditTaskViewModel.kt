package com.example.tasktraker.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktraker.alarms.TaskAlarmManager
import com.example.tasktraker.models.AddEditTaskModal
import com.example.tasktraker.models.Task
import com.example.tasktraker.models.TaskCategory
import com.example.tasktraker.models.TaskPriority
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SaveDeleteTaskUIEvent {
    object NavigateBack : SaveDeleteTaskUIEvent()
    data class Error(val message: String) : SaveDeleteTaskUIEvent()
}

class AddEditTaskViewModel(
    private val repository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager
) : ViewModel() {

    private val _addEditTaskUIState = MutableStateFlow(AddEditTaskModal())
    val addEditTaskUIState = _addEditTaskUIState.asStateFlow()

    private val _saveDeleteTaskEvent = MutableSharedFlow<SaveDeleteTaskUIEvent>()
    val saveDeleteTaskEvent = _saveDeleteTaskEvent.asSharedFlow()

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

    fun isRemindMeUpdate() {
        _addEditTaskUIState.value =
            _addEditTaskUIState.value.copy(isRemindMe = !(_addEditTaskUIState.value.isRemindMe))
    }

    fun onPriorityUpdate(priority: TaskPriority) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(priority = priority)
    }

    fun onFileUriUpdate(fileUri: String?) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(fileLocation = fileUri)
    }

    fun onFileNameUpdate(fileName: String?) {
        _addEditTaskUIState.value = _addEditTaskUIState.value.copy(fileName = fileName)
    }

    fun loadTask(taskId: Long) {
        if (taskId == -1L) {
            _addEditTaskUIState.value = AddEditTaskModal()
            return
        }
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

    fun canScheduleExactAlarms(): Boolean {
        return taskAlarmManager.canScheduleExactAlarms()
    }

    fun saveTask() {
        viewModelScope.launch {
            val state = _addEditTaskUIState.value
            val task = Task(
                id = state.id ?: 0,
                taskName = state.taskName,
                taskDescription = state.taskDescription,
                category = state.category,
                dueDate = state.dueDate,
                isRemindMe = state.isRemindMe,
                priority = state.priority,
                isCompleted = state.isCompleted,
                fileName = state.fileName?:"",
                fileLocation = state.fileLocation?:""
            )
            try {
                if (state.id == null) {
                    val newId = repository.insertTask(task)
                    taskAlarmManager.scheduleTaskAlarm(task.copy(id = newId))
                } else {
                    repository.updateTask(task)
                    taskAlarmManager.scheduleTaskAlarm(task)
                }
                _saveDeleteTaskEvent.emit(SaveDeleteTaskUIEvent.NavigateBack)
            } catch (e: Exception) {
                _saveDeleteTaskEvent.emit(SaveDeleteTaskUIEvent.Error("Something went wrong. Try Again ${e.message}"))
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.deleteTask(task)
                taskAlarmManager.cancelTaskAlarm(task)
                _saveDeleteTaskEvent.emit(SaveDeleteTaskUIEvent.NavigateBack)
            } catch (e: Exception) {
                _saveDeleteTaskEvent.emit(SaveDeleteTaskUIEvent.Error("Something went wrong. Try Again ${e.message}"))
            }
        }
    }
}