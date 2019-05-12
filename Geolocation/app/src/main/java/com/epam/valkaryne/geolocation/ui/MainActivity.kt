package com.epam.valkaryne.geolocation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.viewmodel.GeoViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

/**
 * [MainActivity] that contains two fragments: one with interactive map, and one with controls.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    private var viewModel: GeoViewModel? = null

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(GeoViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkRequiredPermissions()

        setFragment(R.id.controls_container, ControlsFragment())
        setFragment(R.id.map_container, MapFragment())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RQS_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    subscribeLocationData()
                else
                    finish()
            }
        }
    }

    private fun checkRequiredPermissions() {
        val isFineLocationGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        val isCoarseLocationGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        if (!isFineLocationGranted && !isCoarseLocationGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), RQS_LOCATION_PERMISSION
            )
        } else {
            subscribeLocationData()
        }
    }

    @SuppressLint("MissingPermission")
    private fun subscribeLocationData() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 3000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, FusedLocationCallback(), null)
    }

    private fun setFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    inner class FusedLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            val lastLocation = locationResult.lastLocation
            viewModel?.userLatLng?.value = LatLng(lastLocation.latitude, lastLocation.longitude)
        }
    }

    companion object {
        const val RQS_LOCATION_PERMISSION = 100
    }
}
