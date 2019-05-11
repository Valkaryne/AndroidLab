package com.epam.valkaryne.alarmclock.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.epam.valkaryne.alarmclock.model.AlarmHandler

/**
 * Broadcast receiver responsible for restoring setted alarm after reboot the device.
 *
 * @author Valentine Litvin
 */

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            val target = AlarmHandler.loadAlarm(context)
            target?.let { AlarmHandler.setAlarm(context, it) }
        }
    }
}