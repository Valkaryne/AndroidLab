package com.epam.valkaryne.weatherforecasting

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class ForecastItemAdapter(private val cities: Array<CityInfo>) : RecyclerView.Adapter<ForecastItemAdapter.ViewHolder>() {

    private var context: Context? = null
    private val transitionFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false) as ConstraintLayout
        this.context = parent.context
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = holder.item
        val ivCity = itemView.findViewById<ImageView>(R.id.iv_city)
        val tvTemperature = itemView.findViewById<TextView>(R.id.tv_temperature)
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        tvName.text = cities[position].cityName
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
        tvInfo.text = cities[position].cityInfo
        val ivWeather = itemView.findViewById<ImageView>(R.id.iv_weather)
        ivWeather.setImageDrawable(getWeatherDrawable(cities[position]))

        context?.let { Glide.with(it)
            .load(cities[position].cityImage).apply(RequestOptions.circleCropTransform())
            .transition(withCrossFade(transitionFactory))
            .placeholder(R.drawable.city_variant).error(R.drawable.city_error).into(ivCity)
        tvTemperature.text = String.format(it.getString(R.string.temperature_ph),
            cities[position].weatherData.temperature)
        }
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

    inner class ViewHolder(val item: ConstraintLayout) : RecyclerView.ViewHolder(item)
}