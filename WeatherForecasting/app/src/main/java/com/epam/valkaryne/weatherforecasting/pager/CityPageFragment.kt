package com.epam.valkaryne.weatherforecasting.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.epam.valkaryne.weatherforecasting.R
import com.epam.valkaryne.weatherforecasting.model.CityInfo

class CityPageFragment : Fragment() {

    private var cityInfo: CityInfo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvCityName = view.findViewById<TextView>(R.id.tv_city_name)
        val ivCityImage = view.findViewById<ImageView>(R.id.iv_city_image)
        val tvCityDescription = view.findViewById<TextView>(R.id.tv_city_description)

        cityInfo?.let {
            tvCityName.text = it.cityName
            Glide.with(context!!)
                    .load(it.cityImage)
                    .placeholder(R.drawable.city_variant).error(R.drawable.city_error)
                    .into(ivCityImage)
            tvCityDescription.text = it.cityDescription
        }
    }

    companion object {
        fun newInstance(cityInfo: CityInfo) : CityPageFragment {
            val fragment = CityPageFragment()
            fragment.cityInfo = cityInfo

            return fragment
        }
    }
}