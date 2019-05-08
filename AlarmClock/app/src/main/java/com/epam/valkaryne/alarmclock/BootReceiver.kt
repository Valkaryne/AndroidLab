package com.epam.valkaryne.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import java.util.*
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)

            if (prefs.contains(Constants.PREF_ALARM_TIME)) {
                val calendar = Calendar.getInstance()
                val time = prefs.getLong(Constants.PREF_ALARM_TIME, 0)
                calendar.timeInMillis = time
                setAlarm(calendar, context)
            }
        }
    }

    private fun setAlarm(targetCalendar: Calendar, context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager?.let {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, Constants.RQS_ALARM, intent, 0)
            it.setRepeating(
                AlarmManager.RTC_WAKEUP, targetCalendar.timeInMillis,
                TimeUnit.MINUTES.toMillis(5), pendingIntent)
        }
    }
}