package com.epam.valkaryne.alarmclock

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import java.util.*

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

        displayAlarmTime()
    }

    private fun displayAlarmTime() {
        val alarmTime = AlarmHandler.loadAlarm(context)

        if (alarmTime == null)
            tvAlarmTime?.text = getString(R.string.no_alarm)
        else
            tvAlarmTime?.text = alarmTime.getAlarmFormattedTime()
    }

    inner class TimeSetListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            val target = Calendar.getInstance()

            timePicker?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    target.set(Calendar.HOUR_OF_DAY, it.hour)
                    target.set(Calendar.MINUTE, it.minute)
                } else {
                    target.set(Calendar.HOUR_OF_DAY, it.currentHour)
                    target.set(Calendar.MINUTE, it.currentMinute)
                }
            }
            target.set(Calendar.SECOND, 0)
            target.set(Calendar.MILLISECOND, 0)

            AlarmHandler.setAlarm(context, target)
            displayAlarmTime()
        }
    }

    inner class AlarmResetListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            AlarmHandler.resetAlarm(context)
            displayAlarmTime()
        }
    }

}