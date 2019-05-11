package com.epam.valkaryne.geolocation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.viewmodel.GeoViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText

class ControlsFragment : Fragment() {

    private var viewModel: GeoViewModel? = null

    private var etLatitude: TextInputEditText? = null
    private var etLongitude: TextInputEditText? = null

    private val targetObserver =
        Observer<LatLng> { point ->
            etLatitude?.setText(point.latitude.toString())
            etLongitude?.setText(point.longitude.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { viewModel = ViewModelProviders.of(it).get(GeoViewModel::class.java) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etLatitude = view.findViewById(R.id.et_latitude)
        etLongitude = view.findViewById(R.id.et_longitude)

        viewModel?.targetLatLng?.observe(this, targetObserver)

        val btnSet = view.findViewById<Button>(R.id.btn_set_marker)
        btnSet.setOnClickListener {
            val lat = etLatitude?.text?.toString()?.toDouble()
            val lng = etLongitude?.text?.toString()?.toDouble()
            if ((lat != null) && (lng != null))
                viewModel?.targetLatLng?.value = LatLng(lat, lng)
        }

        val btnReset = view.findViewById<Button>(R.id.btn_reset_marker)
        btnReset.setOnClickListener {
            viewModel?.geoRequest?.value = GeoViewModel.RQS_RESET_TARGET
        }
    }
}