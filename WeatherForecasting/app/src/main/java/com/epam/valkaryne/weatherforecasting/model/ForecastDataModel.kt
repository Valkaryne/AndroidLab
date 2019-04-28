package com.epam.valkaryne.weatherforecasting.model

import com.epam.valkaryne.weatherforecasting.ForecastItemAdapter

class ForecastDataModel(private val forecastElements: MutableList<ForecastElement>,
                        private val adapter: ForecastItemAdapter) {
    private val forecastBaseList: List<ForecastElement> = forecastElements.toList()
    private val forecastExtensionList: MutableList<ForecastElement> = ArrayList()

    fun addFavorite(position: Int) {
        val city = forecastElements[position] as CityInfo
        city.isFavorite = true
        if (forecastExtensionList.size == 0) {
            forecastExtensionList.add(0,
                Section("Favorites")
            )
            forecastExtensionList.add(1, Section("All"))
        }
        forecastExtensionList.add(1, city)
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
        if (forecastExtensionList.size > 2)
            forecastElements.addAll(forecastExtensionList)
        forecastElements.addAll(forecastBaseList)
        adapter.notifyDataSetChanged()
    }
}