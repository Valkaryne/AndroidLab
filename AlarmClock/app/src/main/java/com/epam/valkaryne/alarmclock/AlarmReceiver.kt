package com.epam.valkaryne.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            AlarmHandler.ACTION_ALARM -> {
                val scheduledIntent = Intent(context, AlarmActivity::class.java)
                scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(scheduledIntent)

                AlarmHandler.cancelNotification(context)
                postponeAlarm(context)
            }
            AlarmHandler.ACTION_NOTIFICATION -> {
                AlarmHandler.showNotification(context)
            }
            AlarmHandler.ACTION_CANCEL -> {
                AlarmHandler.cancelNotification(context)
                postponeAlarm(context)
            }
        }
    }

    private fun postponeAlarm(context: Context?) {
        val target = AlarmHandler.loadAlarm(context)
        target?.let {
            it.add(Calendar.DATE, AlarmHandler.DAY_INTERVAL)
            AlarmHandler.setAlarm(context, target)
        }

    }
}