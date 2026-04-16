package com.example.tasktraker.repository

import com.example.tasktraker.databases.dao.TaskDao
import com.example.tasktraker.databases.entity.toDomain
import com.example.tasktraker.databases.entity.toEntity
import com.example.tasktraker.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks().map { tasks ->
        tasks.map { it.toDomain() }
    }

    suspend fun insertTask(task: Task) = taskDao.insertTask(task = task.toEntity())

    suspend fun updateTask(task: Task) = taskDao.updateTask(task = task.toEntity())

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task = task.toEntity())

    suspend fun getTaskById(id: Long) = taskDao.getTaskById(id)
}