package com.epam.valkaryne.geolocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class GeoViewModel : ViewModel() {

    val targetLatLng: MutableLiveData<LatLng> = MutableLiveData()
    val geoRequest: MutableLiveData<Int> = MutableLiveData()

    companion object {
        const val RQS_RESET_TARGET = 180
    }
}

