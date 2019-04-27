package com.epam.valkaryne.weatherforecasting

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ForecastItemAdapter(private val cities: Array<CityInfo>) : RecyclerView.Adapter<ForecastItemAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false) as CardView
        this.context = parent.context
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val ivCity = cardView.findViewById<ImageView>(R.id.iv_city)
        context?.let { Glide.with(it)
            .load(cities[position].cityImage).into(ivCity) }
        val tvName = cardView.findViewById<TextView>(R.id.tv_name)
        tvName.text = cities[position].cityName
        val tvInfo = cardView.findViewById<TextView>(R.id.tv_info)
        tvInfo.text = cities[position].cityInfo
        val ivWeather = cardView.findViewById<ImageView>(R.id.iv_weather)
        ivWeather.setImageDrawable(getWeatherDrawable(cities[position]))
        val tvTemperature = cardView.findViewById<TextView>(R.id.tv_temperature)
        tvTemperature.text = cities[position].weatherData.temperature.toString()
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    private fun getWeatherDrawable(cityInfo: CityInfo) : Drawable? {
        var weatherDrawable: Drawable? = null
        context?.let {
            weatherDrawable = when (cityInfo.weatherData.type) {
                WeatherData.WeatherType.SUNNY -> ContextCompat.getDrawable(it, R.drawable.weather_sunny)
                WeatherData.WeatherType.CLOUDS -> ContextCompat.getDrawable(it, R.drawable.weather_cloudy)
                WeatherData.WeatherType.RAIN -> ContextCompat.getDrawable(it, R.drawable.weather_rainy)
                WeatherData.WeatherType.STORM -> ContextCompat.getDrawable(it, R.drawable.weather_storm)
                WeatherData.WeatherType.SNOW -> ContextCompat.getDrawable(it, R.drawable.weather_snowy)
            }
        }
        return weatherDrawable
    }

    inner class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)
}