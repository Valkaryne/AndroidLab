package com.epam.valkaryne.geolocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

/**
 * ViewModel to keep state of MainActivity and its Fragments. Contains Geo Data.
 *
 * @author Valentine Litvin
 */
class GeoViewModel : ViewModel() {

    val targetLatLng: MutableLiveData<LatLng> = MutableLiveData()
    val geoRequest: MutableLiveData<Int> = MutableLiveData()

    companion object {
        const val RQS_RESET_TARGET = 180
    }
}

