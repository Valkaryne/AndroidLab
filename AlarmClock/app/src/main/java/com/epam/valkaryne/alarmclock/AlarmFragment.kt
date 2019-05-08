package com.epam.valkaryne.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmFragment : Fragment() {

    private var timePicker: TimePicker? = null
    private var tvAlarmTime: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timePicker = view.findViewById(R.id.time_picker)
        tvAlarmTime = view.findViewById(R.id.tv_alarm_time)

        val buttonSetAlarm = view.findViewById<Button>(R.id.btn_set)
        buttonSetAlarm.setOnClickListener(TimeSetListener())

        val buttonResetAlarm = view.findViewById<Button>(R.id.btn_reset)
        buttonResetAlarm.setOnClickListener(AlarmResetListener())

        loadAlarm()
    }

    private fun saveAlarm(calendar: Calendar) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()

        editor.putLong(Constants.PREF_ALARM_TIME, calendar.timeInMillis)
        editor.apply()
    }

    private fun loadAlarm() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        if (prefs.contains(Constants.PREF_ALARM_TIME)) {
            val calendar = Calendar.getInstance()
            val time = prefs.getLong(Constants.PREF_ALARM_TIME, 0)
            calendar.timeInMillis = time
            tvAlarmTime?.text = calendar.getAlarmFormattedTime()
        }
    }

    private fun clearAlarm() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        if (prefs.contains(Constants.PREF_ALARM_TIME))
            editor.remove(Constants.PREF_ALARM_TIME)
        editor.apply()
    }

    private fun setAlarm(targetCalendar: Calendar) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager?.let {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, Constants.RQS_ALARM, intent, 0)
            it.setRepeating(AlarmManager.RTC_WAKEUP, targetCalendar.timeInMillis,
                TimeUnit.MINUTES.toMillis(5), pendingIntent)
            tvAlarmTime?.text = targetCalendar.getAlarmFormattedTime()
            saveAlarm(targetCalendar)
            setBootReceiverEnabled(true)
        }
    }

    private fun resetAlarm() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager?.let {
            tvAlarmTime?.text = getString(R.string.no_alarm)

            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, Constants.RQS_ALARM, intent, 0)
            it.cancel(pendingIntent)
            clearAlarm()
            setBootReceiverEnabled(false)
        }
    }

    private fun setBootReceiverEnabled(stateEnabled: Boolean) {
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

    inner class TimeSetListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            val now = Calendar.getInstance()
            val next = now.clone() as Calendar

            timePicker?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    next.set(Calendar.HOUR_OF_DAY, it.hour)
                    next.set(Calendar.MINUTE, it.minute)
                } else {
                    next.set(Calendar.HOUR_OF_DAY, it.currentHour)
                    next.set(Calendar.MINUTE, it.currentMinute)
                }
            }
            next.set(Calendar.SECOND, 0)
            next.set(Calendar.MILLISECOND, 0)

            if (next <= now)
                next.add(Calendar.DATE, 1)

            setAlarm(next)
        }
    }

    inner class AlarmResetListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            resetAlarm()
        }
    }

}