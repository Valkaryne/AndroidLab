package com.epam.valkaryne.airlines

data class FlightData(val departPoint: String,
                      val arrivalPoint: String,
                      val date: String,
                      val price: Int,
                      val takeoffTime: String,
                      val landingTime: String,
                      val duration: String,
                      val freeSeats: Int,
                      val currency: String = "BYN") {

    companion object {
        const val DEPART_POINT = "depart"
        const val ARRIVAL_POINT = "arrival"
        const val DATE = "date"
        const val PRICE = "price"
        const val TAKEOFF_TIME = "takeoff"
        const val LANDING_TIME = "landing"
        const val DURATION = "duration"
        const val FREE_SEATS = "free"
        const val CURRENCY = "currency"

        const val DEPART_MOD = "_dep"
        const val RETURN_MOD = "_ret"
    }
}