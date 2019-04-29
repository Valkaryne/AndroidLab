package com.epam.valkaryne.weatherforecasting.model

import com.epam.valkaryne.weatherforecasting.ForecastItemAdapter
import com.epam.valkaryne.weatherforecasting.R

class ForecastDataModel(private val forecastElements: MutableList<ForecastElement>,
                        private val adapter: ForecastItemAdapter) {
    private val forecastBaseList: List<ForecastElement> = forecastElements.toList()
    private val forecastExtensionList: MutableList<ForecastElement> = ArrayList()

    fun addFavorite(position: Int) {
        val city = forecastElements[position] as CityInfo
        city.isFavorite = true
        forecastExtensionList.add(0, city)
        refreshData()
    }

    fun removeFavorite(position: Int) {
        val city = forecastElements[position] as CityInfo
        city.isFavorite = false
        forecastExtensionList.remove(city)
        refreshData()
    }

    private fun refreshData() {
        forecastElements.clear()
        if (forecastExtensionList.size > 0) {
            adapter.context?.let { 
                forecastElements.add(0, Section(it.getString(R.string.favorites_section)))
                forecastElements.add(1, Section(it.getString(R.string.all_section)))
            }
            forecastElements.addAll(1, forecastExtensionList)
        }
        forecastElements.addAll(forecastBaseList)
        adapter.notifyDataSetChanged()
    }
}