package com.epam.valkaryne.weatherforecasting

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.epam.valkaryne.weatherforecasting.model.*
import com.epam.valkaryne.weatherforecasting.pager.PagerFragment

/**
 * Special Adapter puts items with cities and their weather to the RecyclerView
 *
 * @author Valentine Litvin
 */

class ForecastItemAdapter(cities: Array<CityInfo>, private val manager: FragmentManager, val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //var context: Context? = null
    private val transitionFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    private val forecastElements: MutableList<ForecastElement> = cities.toMutableList()
    private val forecastItemListener = ForecastItemListener()
    val forecastDataModel = ForecastDataModel(forecastElements, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //this.context = parent.context

        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.item_forecast, parent, false) as ConstraintLayout

        val sectionView = LayoutInflater.from(context)
            .inflate(R.layout.section_forecast, parent, false) as LinearLayout

        return when (viewType) {
            ITEM -> ViewHolder(itemView)
            else -> SectionHolder(sectionView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val city = forecastElements[position] as CityInfo
                val itemView = holder.item
                val ivCity = itemView.findViewById<ImageView>(R.id.iv_city)
                val tvTemperature = itemView.findViewById<TextView>(R.id.tv_temperature)
                val tvName = itemView.findViewById<TextView>(R.id.tv_name)
                tvName.text = city.cityName
                val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
                tvInfo.text = city.cityInfo
                val ivWeather = itemView.findViewById<ImageView>(R.id.iv_weather)
                ivWeather.setImageDrawable(getWeatherDrawable(city))

                context?.let { Glide.with(it)
                    .load(city.cityImage).apply(RequestOptions.circleCropTransform())
                    .transition(withCrossFade(transitionFactory))
                    .placeholder(R.drawable.city_variant).error(R.drawable.city_error).into(ivCity)
                    tvTemperature.text = String.format(it.getString(R.string.temperature_ph),
                        city.weatherData.temperature)
                }
                itemView.setOnClickListener {
                    forecastItemListener.onItemClick(position)
                }
                itemView.setOnLongClickListener {
                    if (city.isFavorite) forecastItemListener.onItemLongClick(position, false)
                    else forecastItemListener.onItemLongClick(position, true)
                }
            }
            is SectionHolder -> {
                val section = forecastElements[position] as Section
                val sectionView = holder.section
                val tvSectionName = sectionView.findViewById<TextView>(R.id.tv_section_name)
                tvSectionName.text = section.name
            }
        }
    }

    override fun getItemCount(): Int {
        return forecastElements.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(forecastElements[position].isSection) {
            true -> SECTION
            else -> ITEM
        }
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

    inner class SectionHolder(val section: LinearLayout) : RecyclerView.ViewHolder(section)

    inner class ForecastItemListener : ItemClickListener {

        override fun onItemClick(position: Int) {
            val pagerFragment = PagerFragment.newInstance(forecastElements[position] as CityInfo)
            manager.beginTransaction()
                .replace(R.id.fragmentContainer, pagerFragment)
                .addToBackStack(null)
                .commit()
        }

        override fun onItemLongClick(position: Int, addItem: Boolean) : Boolean {
            val dialogFragment = FavoritesDialog.newInstance(forecastElements[position].name, position, addItem, forecastDataModel)
            dialogFragment.show(manager.beginTransaction(), "dialog")
            return true
        }
    }

    private companion object {
        const val SECTION = 0
        const val ITEM = 1
    }
}