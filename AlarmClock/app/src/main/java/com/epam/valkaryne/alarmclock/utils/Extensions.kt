package com.epam.valkaryne.alarmclock.utils

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getAlarmFormattedTime() : String{
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(this.time)
}