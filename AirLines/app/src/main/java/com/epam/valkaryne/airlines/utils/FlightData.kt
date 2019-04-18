package com.epam.valkaryne.airlines.utils

import java.time.LocalDate
import java.util.*

data class FlightData(val inDepart: String,
                      val inArrival: String,
                      val inDate: LocalDate,
                      val price: Int,
                      val inTakeoffTime: FlightTime,
                      val inLandingTime: FlightTime,
                      val freeSeats: Int,
                      val inCurrency: Currency = Currency.getInstance("BYR")) {

    var depart : String = inDepart
        set(value) {
            field = if (value.length > 3)
                value.substring(0, 3).capitalize()
            else
                value.capitalize()
        }

    var arrival : String = inArrival
        set(value) {
            field = if (value.length > 3)
                value.substring(0, 3).capitalize()
            else
                value.capitalize()
        }

    var date : String = "${inDate.dayOfMonth} ${inDate.month.toString().substring(0,3)} ${inDate.year}"

    var takeoffTime : String = inTakeoffTime.time

    var landingTime : String = inLandingTime.time

    var duration : String = (inLandingTime - inTakeoffTime).time

    var currency : String = inCurrency.currencyCode
}