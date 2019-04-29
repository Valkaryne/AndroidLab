package com.epam.valkaryne.weatherforecasting

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.valkaryne.weatherforecasting.model.CityInfo

class ForecastFragment : Fragment() {

    private var adapter: ForecastItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val forecastRecycler = inflater.inflate(
            R.layout.fragment_forecast, container, false
        ) as RecyclerView

        adapter = ForecastItemAdapter(CityInfo.cities, fragmentManager!!)
        forecastRecycler.adapter = adapter
        forecastRecycler.layoutManager = LinearLayoutManager(activity)

        val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        forecastRecycler.addItemDecoration(itemDecor)
        return forecastRecycler
    }

    fun sortForecastData(sortMode: Int) {
        when (sortMode) {
            SORT_ALPHABETIC_ASCEND -> adapter?.forecastDataModel?.sortAscendingByAlphabet()
            SORT_ALPHABETIC_DESCNED -> adapter?.forecastDataModel?.sortDescendingByAlphabet()
            SORT_NUMERIC_ASCEND -> adapter?.forecastDataModel?.sortAscendingByTemperature()
            SORT_NUMERIC_DESCEND -> adapter?.forecastDataModel?.sortDescendingByTemperature()
        }
    }

    companion object {
        const val SORT_ALPHABETIC_ASCEND = 120
        const val SORT_ALPHABETIC_DESCNED = 121
        const val SORT_NUMERIC_ASCEND = 122
        const val SORT_NUMERIC_DESCEND = 123
    }
}