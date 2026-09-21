package com.example.tasktraker.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import com.example.tasktraker.notifications.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val id = intent.getLongExtra("EXTRA_ID", -1L)
        if (id == -1L || context == null) return

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "TaskTracker:AlarmWakeLock"
        )

        // Acquire wake lock for 10 seconds to ensure notification handoff
        wakeLock.acquire(10_000L)

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(id, "Task Deadline", message)
    }
}
