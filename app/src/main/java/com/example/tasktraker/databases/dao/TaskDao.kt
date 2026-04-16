package com.example.tasktraker.databases.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktraker.databases.entity.TaskEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("Select * from task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("Select * from task where id = :id")
    suspend fun getTaskById(id: Long)

}