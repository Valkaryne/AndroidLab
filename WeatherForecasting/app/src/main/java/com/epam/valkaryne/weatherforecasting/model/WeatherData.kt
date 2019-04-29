package com.epam.valkaryne.weatherforecasting.model

/**
 * Class describes weather conditions
 *
 * @author Valentine Litvin
 */
data class WeatherData(val type: WeatherType, val temperature: Int) {
    enum class WeatherType{
        SUNNY, CLOUDS, RAIN, STORM, SNOW
    }
}