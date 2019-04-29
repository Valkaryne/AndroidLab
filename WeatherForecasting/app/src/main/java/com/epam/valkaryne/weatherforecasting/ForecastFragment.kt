package com.epam.valkaryne.weatherforecasting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.valkaryne.weatherforecasting.model.CityInfo

class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val forecastRecycler = inflater.inflate(
            R.layout.fragment_forecast, container, false
        ) as RecyclerView

        val adapter = ForecastItemAdapter(CityInfo.cities, fragmentManager!!)
        forecastRecycler.adapter = adapter
        forecastRecycler.layoutManager = LinearLayoutManager(activity)

        val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        forecastRecycler.addItemDecoration(itemDecor)
        return forecastRecycler
    }
}