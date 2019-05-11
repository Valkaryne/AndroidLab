package com.epam.valkaryne.geolocation.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.epam.valkaryne.geolocation.viewmodel.GeoViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : SupportMapFragment() {

    private var viewModel: GeoViewModel? = null
    private var map: GoogleMap? = null
    private var target: Marker? = null

    private val targetObserver =
        Observer<LatLng> { point ->
            target?.remove()
            val markerOptions = MarkerOptions()
            point?.let { markerOptions.position(it) }
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            target = map?.addMarker(markerOptions)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { viewModel = ViewModelProviders.of(it).get(GeoViewModel::class.java) }

        getMapAsync(MapReadyCallback())
    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap?) {
            map = googleMap
            map?.setOnMapClickListener(MapClickListener())

            val htp = LatLng(53.927052, 27.681375)
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(htp, 16F))

            viewModel?.targetLatLng?.observe(this@MapFragment, targetObserver)
        }
    }

    inner class MapClickListener : GoogleMap.OnMapClickListener {
        override fun onMapClick(point: LatLng?) {
            viewModel?.targetLatLng?.value = point

            target?.remove()
            val markerOptions = MarkerOptions()
            point?.let { markerOptions.position(it) }
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            target = map?.addMarker(markerOptions)
        }
    }
}