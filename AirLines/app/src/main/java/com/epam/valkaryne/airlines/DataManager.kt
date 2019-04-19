package com.epam.valkaryne.airlines

import com.epam.valkaryne.airlines.utils.FlightData
import com.epam.valkaryne.airlines.utils.FlightTime
import java.time.LocalDate

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
                LocalDate.of(2019, 9, 16),
                435, FlightTime(0, 20),
                FlightTime(9, 20), 3)
        }

        fun returnFlightData() : FlightData {
            return FlightData(
                "FLO", "MSQ",
                LocalDate.of(2019, 9, 17),
                488, FlightTime(5, 10),
                FlightTime(9, 20),5)
        }
    }
}