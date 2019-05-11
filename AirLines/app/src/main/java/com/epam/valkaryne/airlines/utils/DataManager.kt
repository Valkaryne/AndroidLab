package com.epam.valkaryne.airlines.utils

/**
 * Simple class to return flight data with standard parameters.
 *
 * @author Valentine Litvin
 */

class DataManager {
    companion object {
        fun departFlightData() : FlightData {
            return FlightData(
                "MSQ", "FLO",
                "16 SEP 2019",
                435, "00:20",
                "09:20", "9:00", 3, "BYN")
        }

        fun returnFlightData() : FlightData {
            return FlightData(
                "FLO", "MSQ",
                "17 SEP 2019",
                488, "05:10",
                "09:20", "4:10", 5, "BYN")
        }
    }
}