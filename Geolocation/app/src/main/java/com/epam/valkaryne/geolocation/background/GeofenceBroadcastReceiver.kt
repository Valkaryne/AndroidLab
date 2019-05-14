package com.epam.valkaryne.geolocation.background

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.utils.createNotification

/**
 * Broadcast receiver pending signal of entering desired area. It sends notification with standard
 * message.
 *
 * @author Valentine Litvin
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            sendNotification(it, it.getString(R.string.standard_enter_message))
        }
    }

    private fun sendNotification(context: Context, message: String) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification =
            createNotification(context, message)

        notificationManager.notify(EVENT_ENTER_ID, notification)
    }

    companion object {
        const val EVENT_ENTER_ID = 1034
    }
}