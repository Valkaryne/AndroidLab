package com.epam.valkaryne.alarmclock

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*

class AlarmHandler {
    companion object {
        private const val RQS_ALARM = 1
        private const val RQS_NOTIFICATION = 2
        private const val RQS_CANCEL = 3

        private const val PREF_ALARM_TIME = "alarm_time"
        const val DAY_INTERVAL = 1

        private const val CHANNEL_ID = "notification_0"
        private const val CHANNEL_NAME = "alarm_notification_channel"
        private const val NOTIFICATION_ID = 101

        const val ACTION_ALARM = "action_alarm"
        const val ACTION_NOTIFICATION = "action_notification"
        const val ACTION_CANCEL = "action_cancel"

        fun setAlarm(context: Context?, targetCalendar: Calendar) {
            val now = Calendar.getInstance()
            if (now >= targetCalendar)
                targetCalendar.add(Calendar.DATE, 1)

            sendSignal(context, targetCalendar, ACTION_ALARM, RQS_ALARM)
            saveAlarm(context, targetCalendar)
            setBootReceiverEnabled(context, true)

            setNotification(context, targetCalendar)
        }

        fun resetAlarm(context: Context?) {
            interruptSignal(context, ACTION_ALARM, RQS_ALARM)
            cancelNotification(context)

            clearAlarm(context)
            setBootReceiverEnabled(context, false)
        }

        fun showNotification(context: Context?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                createNotificationChannel(context)

            val notification = createNotification(context)
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            notificationManager?.notify(NOTIFICATION_ID, notification)
        }

        fun cancelNotification(context: Context?) {
            interruptSignal(context, ACTION_NOTIFICATION, RQS_NOTIFICATION)

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            notificationManager?.cancel(NOTIFICATION_ID)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun createNotificationChannel(context: Context?) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            manager?.createNotificationChannel(channel)
        }

        fun saveAlarm(context: Context?, calendar: Calendar) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()

            editor.putLong(PREF_ALARM_TIME, calendar.timeInMillis)
            editor.apply()
        }

        fun loadAlarm(context: Context?) : Calendar? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)

            if (prefs.contains(PREF_ALARM_TIME)) {
                val calendar = Calendar.getInstance()
                val millis = prefs.getLong(PREF_ALARM_TIME, 0)
                calendar.timeInMillis = millis

                return calendar
            }
            return null
        }

        fun clearAlarm(context: Context?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()

            if (prefs.contains(PREF_ALARM_TIME))
                editor.remove(PREF_ALARM_TIME)
            editor.apply()
        }

        private fun setNotification(context: Context?, target: Calendar) {
            target.add(Calendar.MINUTE, -5)

            sendSignal(context, target, ACTION_NOTIFICATION, RQS_NOTIFICATION)
        }

        private fun createNotification(context: Context?) : Notification {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = ACTION_CANCEL
            val pendingIntent = PendingIntent.getBroadcast(context, RQS_CANCEL, intent, 0)

            return NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.alarm_clock_title))
                .setContentText(loadAlarm(context)?.getAlarmFormattedTime())
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_close, context.getString(R.string.cancel_action),
                    pendingIntent)
                .build()
        }

        private fun sendSignal(context: Context?, calendar: Calendar, action: String, request: Int) {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            alarmManager?.let {
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.action = action
                val pendingIntent = PendingIntent.getBroadcast(
                    context, request, intent, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis, pendingIntent)
                } else {
                    it.setExact(AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis, pendingIntent)
                }
            }
        }

        private fun interruptSignal(context: Context?, action: String, request: Int) {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            alarmManager?.let {
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.action = action
                val pendingIntent = PendingIntent.getBroadcast(
                    context, request, intent, 0)
                it.cancel(pendingIntent)
            }
        }

        private fun setBootReceiverEnabled(context: Context?, stateEnabled: Boolean) {
            context?.let {
                val receiver = ComponentName(it, BootReceiver::class.java)
                val pm = it.packageManager

                if (stateEnabled)
                    pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP)
                else
                    pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP)
            }
        }
    }
}