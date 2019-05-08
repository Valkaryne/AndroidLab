package com.epam.valkaryne.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Ding-ding! Your time is over!",
            Toast.LENGTH_LONG).show()
    }
}