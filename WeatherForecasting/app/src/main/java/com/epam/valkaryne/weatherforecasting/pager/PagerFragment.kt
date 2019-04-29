package com.epam.valkaryne.weatherforecasting.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.epam.valkaryne.weatherforecasting.R
import com.epam.valkaryne.weatherforecasting.model.CityInfo

class PagerFragment : Fragment() {

    private var cityInfo: CityInfo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forecastPager = view.findViewById<ViewPager>(R.id.forecast_pager)
        val pagerAdapter = ForecastPagerAdapter(fragmentManager, cityInfo!!)
        forecastPager.adapter = pagerAdapter
    }

    companion object {
        fun newInstance(cityInfo: CityInfo) : PagerFragment {
            val fragment = PagerFragment()
            fragment.cityInfo = cityInfo

            return fragment
        }
    }
}