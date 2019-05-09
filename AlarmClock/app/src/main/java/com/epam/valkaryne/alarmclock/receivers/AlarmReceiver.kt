package com.epam.valkaryne.alarmclock.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.epam.valkaryne.alarmclock.AlarmActivity
import com.epam.valkaryne.alarmclock.model.AlarmHandler
import java.util.*

/**
 * Broadcast receiver of AlarmManager signals. Can behave in three ways:
 *  - starts [AlarmActivity] with ringtone
 *  - shows notification 5 minutes before starting [AlarmActivity]
 *  - postpones alarm for the next time
 *
 *  @author Valentine Litvin
 */

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