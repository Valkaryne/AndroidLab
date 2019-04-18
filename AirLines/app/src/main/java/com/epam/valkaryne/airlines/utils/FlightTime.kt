package com.epam.valkaryne.airlines.utils

data class FlightTime(val inHours: Int, val inMinutes: Int) {

    var hours : Int = inHours
        set(value) {
            if (value >= 0) field = value % 24
        }

    var minutes : Int = inMinutes
        set(value) {
            if (value >= 0) {
                val rem = value / 60
                hours += rem
                field = value % 60
            }
        }

    val time: String
        get() {
            var hs = "$hours"
            var mins = "$minutes"
            if (hs.length < 2) hs = "0$hs"
            if (mins.length < 2) mins = "0$mins"
            return "$hs:$mins"
        }

    operator fun minus(time: FlightTime) : FlightTime {
        val fullMinutes1 = hours + minutes * 60
        val fullMinutes2 = time.hours + time.minutes * 60
        val dif = fullMinutes1 - fullMinutes2
        val hs = dif % 60
        val mins = dif / 60
        return FlightTime(hs, mins)
    }

}