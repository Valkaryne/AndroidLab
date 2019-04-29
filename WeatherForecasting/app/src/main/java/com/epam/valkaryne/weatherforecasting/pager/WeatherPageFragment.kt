package com.epam.valkaryne.weatherforecasting.pager

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.epam.valkaryne.weatherforecasting.R
import com.epam.valkaryne.weatherforecasting.model.CityInfo
import com.epam.valkaryne.weatherforecasting.model.WeatherData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class WeatherPageFragment : Fragment() {
    private var cityInfo: CityInfo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvCurrentTime = view.findViewById<TextView>(R.id.tv_current_time)
        val tvWeatherType = view.findViewById<TextView>(R.id.tv_weather_type)
        val tvRealFeel = view.findViewById<TextView>(R.id.tv_real_feel)
        val tvTemperature = view.findViewById<TextView>(R.id.tv_temperature)
        val ivWeatherType = view.findViewById<ImageView>(R.id.iv_weather_type)
        val tvHumidity = view.findViewById<TextView>(R.id.tv_humidity_value)
        val tvWindSpeed = view.findViewById<TextView>(R.id.tv_wind_speed)

        cityInfo?.let {
            tvCurrentTime.text = getCurrentTime()
            tvWeatherType.text = getWeatherTypeText()
            tvRealFeel.text = String.format(getString(R.string.real_feel_ph, getRealFeel().toString()))
            tvTemperature.text = String.format(getString(R.string.temperature_ph, it.weatherData.temperature.toString()))
            ivWeatherType.setImageDrawable(getWeatherDrawable())
            tvHumidity.text = String.format(getString(R.string.humidity_ph), getHumidity().toString())
            tvWindSpeed.text = String.format(getString(R.string.wind_speed_ph), getWindSpeed().toString())
        }

    }

    private fun getCurrentTime() : String {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("HH:mm z")
        return df.format(currentTime)
    }

    private fun getWeatherTypeText(): String? {
        return when (cityInfo?.weatherData?.type) {
            WeatherData.WeatherType.CLOUDS -> getString(R.string.type_clouds)
            WeatherData.WeatherType.SUNNY -> getString(R.string.type_sunny)
            WeatherData.WeatherType.RAIN -> getString(R.string.type_rain)
            WeatherData.WeatherType.STORM -> getString(R.string.type_storm)
            WeatherData.WeatherType.SNOW -> getString(R.string.type_snow)
            else -> null
        }
    }

    private fun getWeatherDrawable() : Drawable? {
        var weatherDrawable: Drawable? = null
        context?.let {
            weatherDrawable = when (cityInfo?.weatherData?.type) {
                WeatherData.WeatherType.SUNNY -> ContextCompat.getDrawable(it, R.drawable.weather_sunny)
                WeatherData.WeatherType.CLOUDS -> ContextCompat.getDrawable(it, R.drawable.weather_cloudy)
                WeatherData.WeatherType.RAIN -> ContextCompat.getDrawable(it, R.drawable.weather_rainy)
                WeatherData.WeatherType.STORM -> ContextCompat.getDrawable(it, R.drawable.weather_storm)
                WeatherData.WeatherType.SNOW -> ContextCompat.getDrawable(it, R.drawable.weather_snowy)
                else -> null
            }
        }
        return weatherDrawable
    }

    private fun getRealFeel(): Int {
        var real = 0
        cityInfo?.let { val temp = it.weatherData.temperature
            real = temp + Random.nextInt(-3, 3)
        }
        return real
    }

    private fun getHumidity(): Int {
        return when(cityInfo?.weatherData?.type) {
            WeatherData.WeatherType.SUNNY -> Random.nextInt(0, 30)
            WeatherData.WeatherType.CLOUDS -> Random.nextInt(25, 60)
            WeatherData.WeatherType.RAIN -> Random.nextInt(40, 90)
            WeatherData.WeatherType.STORM -> Random.nextInt(35, 85)
            WeatherData.WeatherType.SNOW -> Random.nextInt(25, 60)
            null -> 0
        }
    }

    private fun getWindSpeed(): Int {
        return Random.nextInt(1, 20)
    }

    companion object {
        fun newInstance(cityInfo: CityInfo) : WeatherPageFragment {
            val fragment = WeatherPageFragment()
            fragment.cityInfo = cityInfo

            return fragment
        }
    }
}