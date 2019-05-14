package com.epam.valkaryne.geolocation.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.epam.valkaryne.geolocation.BuildConfig
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.ui.MainActivity

const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.channel}"
const val NOTIFICATION_CHANNEL_NAME = "Geolocation"

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 508

/**
 * Static util functions for work with activity/fragment indifferent components (e.g. notifications).
 *
 * @author Valentine Litvin
 */

fun createNotification(context: Context, message: String): Notification {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
    ) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(channel)
    }

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    return NotificationCompat.Builder(
        context,
        NOTIFICATION_CHANNEL_ID
    )
        .setSmallIcon(R.drawable.ic_my_location)
        .setContentTitle(NOTIFICATION_CHANNEL_NAME)
        .setContentText(message)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
}