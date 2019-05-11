package com.epam.valkaryne.geolocation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.epam.valkaryne.geolocation.viewmodel.GeoViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

/**
 * Fragment responsible for interacting with map.
 *
 * @author Valentine Litvin
 */

class MapFragment : SupportMapFragment() {

    private var viewModel: GeoViewModel? = null
    private var map: GoogleMap? = null
    private var target: Marker? = null
    private var targetArea: Circle? = null

    private val targetObserver =
        Observer<LatLng> { point ->
            val defaultIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            target = target.setMapObject(point, defaultIcon)

            redrawTargetArea(point)
        }

    private val geoRequestObserver =
        Observer<Int> { request ->
            when (request) {
                GeoViewModel.RQS_RESET_TARGET -> target?.remove()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { viewModel = ViewModelProviders.of(it).get(GeoViewModel::class.java) }

        getMapAsync(MapReadyCallback())
    }

    private fun redrawTargetArea(center: LatLng) {
        targetArea?.remove()

        val options = CircleOptions()
            .center(center)
            .radius(100.0)
            .fillColor(Color.parseColor("#3F007FFF"))
            .strokeWidth(2F)
            .strokeColor(Color.parseColor("#7F007FFF"))
        targetArea = map?.addCircle(options)
    }

    fun Marker?.setMapObject(position: LatLng, icon: BitmapDescriptor?): Marker? {
        this?.remove()
        val options = MarkerOptions()
        options.position(position)
        options.icon(icon)
        return map?.addMarker(options)
    }

    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap?) {
            map = googleMap
            map?.setOnMapClickListener(MapClickListener())

            val htp = LatLng(53.927052, 27.681375)
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(htp, 16F))

            viewModel?.targetLatLng?.observe(this@MapFragment, targetObserver)
            viewModel?.geoRequest?.observe(this@MapFragment, geoRequestObserver)
        }
    }

    inner class MapClickListener : GoogleMap.OnMapClickListener {
        override fun onMapClick(point: LatLng) {
            viewModel?.targetLatLng?.value = point

            val defaultIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            target = target.setMapObject(point, defaultIcon)
        }
    }
}