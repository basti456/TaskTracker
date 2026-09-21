package com.example.tasktraker.alarms

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import com.example.tasktraker.models.Task

class TaskAlarmManager(
    private val context: Context,
    private val scheduler: AlarmScheduler
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun scheduleTaskAlarm(task: Task) {
        if (!task.isRemindMe || task.isCompleted) {
            cancelTaskAlarm(task)
            return
        }

        val alarmItem = AlarmItem(
            id = task.id,
            time = task.dueDate,
            message = task.taskName
        )
        scheduler.schedule(alarmItem)
    }

    fun cancelTaskAlarm(task: Task) {
        val alarmItem = AlarmItem(
            id = task.id,
            time = task.dueDate,
            message = task.taskName
        )
        scheduler.cancel(alarmItem)
    }

    fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}
