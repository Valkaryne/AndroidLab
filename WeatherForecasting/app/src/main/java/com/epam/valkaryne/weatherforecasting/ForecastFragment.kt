package com.epam.valkaryne.weatherforecasting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val forecastRecycler = inflater.inflate(
            R.layout.fragment_forecast, container, false
        ) as RecyclerView

        val adapter = ForecastItemAdapter(CityInfo.cities)
        forecastRecycler.adapter = adapter
        forecastRecycler.layoutManager = LinearLayoutManager(activity)
        return forecastRecycler
    }
}