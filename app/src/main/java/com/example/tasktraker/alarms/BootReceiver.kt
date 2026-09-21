package com.example.tasktraker.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tasktraker.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootReceiver : BroadcastReceiver(), KoinComponent {
    
    private val repository: TaskRepository by inject()
    private val taskAlarmManager: TaskAlarmManager by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    repository.getAllTasks().first().forEach { task ->
                        if (task.isRemindMe && !task.isCompleted && task.dueDate > System.currentTimeMillis()) {
                            taskAlarmManager.scheduleTaskAlarm(task)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
