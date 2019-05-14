package com.epam.valkaryne.geolocation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.room.MapObject
import com.epam.valkaryne.geolocation.room.MapObjectDB
import com.epam.valkaryne.geolocation.viewmodel.GeoViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText

/**
 * Fragment that has controls to interact with Geo Data and [MapFragment]. Its controls are:
 *  - EditText to input latitude of target point
 *  - EditText to input longitude of target point
 *  - Button to set target point marker on the map
 *  - Button to reset target point marker from the map
 *  - Switch to enable tracking the user's position relative to target point
 *
 *  @author Valentine Litvin
 */
class ControlsFragment : Fragment() {

    private var viewModel: GeoViewModel? = null
    private var database: MapObjectDB? = null

    private var etLatitude: TextInputEditText? = null
    private var etLongitude: TextInputEditText? = null
    private var swTrack: Switch? = null

    private val targetObserver =
        Observer<LatLng> { point ->
            etLatitude?.setText(point.latitude.toString())
            etLongitude?.setText(point.longitude.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(GeoViewModel::class.java)
            database = Room.databaseBuilder(it, MapObjectDB::class.java, "modb")
                .allowMainThreadQueries()
                .build()
        }
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

        swTrack = view.findViewById(R.id.sw_track)
        swTrack?.setOnCheckedChangeListener { _, b ->
            when (b) {
                true -> {
                    saveLastTrackedTarget()
                    viewModel?.trackingEnabled?.value = true
                }
                false -> {
                    deleteLastTrackedTarget()
                    viewModel?.trackingEnabled?.value = false
                }
            }
        }

        obtainLastTrackedTarget()
    }

    private fun saveLastTrackedTarget() {
        val position = viewModel?.targetLatLng?.value
        position?.let {
            database?.getMapObjectDAO()?.insert(MapObject("target_0", it.latitude, it.longitude))
        }
    }

    private fun obtainLastTrackedTarget() {
        val mapObject = database?.getMapObjectDAO()?.getMapObject("target_0")
        mapObject?.let {
            viewModel?.targetLatLng?.value = LatLng(it.latitude, it.longitude)
            swTrack?.isChecked = true
        }
    }

    private fun deleteLastTrackedTarget() {
        database?.getMapObjectDAO()?.deleteById("target_0")
    }
}