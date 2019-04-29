package com.epam.valkaryne.weatherforecasting.pager

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.epam.valkaryne.weatherforecasting.model.CityInfo

/**
 * Special Adapter puts pages with details to the ViewPager
 *
 * @author Valentine Litvin
 */
class ForecastPagerAdapter(fm: FragmentManager?,
                           cityInfo: CityInfo) : FragmentStatePagerAdapter(fm) {

    private val tabs = SparseArray<Fragment>()

    init {
        tabs.put(0, CityPageFragment.newInstance(cityInfo))
        tabs.put(1, WeatherPageFragment.newInstance(cityInfo))
    }

    override fun getItem(position: Int): Fragment {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.size()
    }
}