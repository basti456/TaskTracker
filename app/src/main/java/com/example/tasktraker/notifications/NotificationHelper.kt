package com.example.tasktraker.notifications

import android.R as R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.tasktraker.MainActivity
import androidx.core.graphics.toColorInt

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "task_deadline_channel_v4" // Force fresh channel for persistent alarm
        const val CHANNEL_NAME = "Task Deadlines (Urgent)"
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Urgent alerts for task deadlines that ring until dismissed"
                setSound(soundUri, audioAttributes)
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setBypassDnd(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification(taskId: Long, title: String, content: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("TASK_ID", taskId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val doneIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_MARK_DONE"
            putExtra("TASK_ID", taskId)
        }
        val donePendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.toInt() + 1000,
            doneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_DISMISS"
            putExtra("TASK_ID", taskId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.toInt() + 2000,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Deadline: $title")
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("$content\n\nThis task is due now. Please complete or dismiss this alert.")
                .setBigContentTitle("Deadline Reached!")
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setFullScreenIntent(pendingIntent, true)
            .setOngoing(true) 
            .setAutoCancel(false) 
            .setLocalOnly(true)
            .addAction(R.drawable.ic_menu_save, "Complete Task", donePendingIntent)
            .addAction(R.drawable.ic_menu_close_clear_cancel, "Stop Alarm", dismissPendingIntent)
            .setColor("#D32F2F".toColorInt())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_INSISTENT

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(taskId.toInt(), notification)
    }
}
