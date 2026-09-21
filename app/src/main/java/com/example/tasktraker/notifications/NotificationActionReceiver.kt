package com.example.tasktraker.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tasktraker.alarms.TaskAlarmManager
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationActionReceiver : BroadcastReceiver(), KoinComponent {

    private val repository: TaskRepository by inject()
    private val taskAlarmManager: TaskAlarmManager by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent?.getLongExtra("TASK_ID", -1L) ?: -1L
        val action = intent?.action

        if (taskId == -1L) return

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(taskId.toInt())

        if (action == "ACTION_MARK_DONE") {
            CoroutineScope(Dispatchers.IO).launch {
                repository.getTaskById(taskId)?.let { task ->
                    val updatedTask = task.copy(isCompleted = true)
                    repository.updateTask(updatedTask)
                    taskAlarmManager.cancelTaskAlarm(updatedTask)
                }
            }
        }
    }
}
