package com.epam.valkaryne.weatherforecasting

data class WeatherData(val type: WeatherType, val temperature: Int) {
    enum class WeatherType{
        SUNNY, CLOUDS, RAIN, STORM, SNOW
    }
}