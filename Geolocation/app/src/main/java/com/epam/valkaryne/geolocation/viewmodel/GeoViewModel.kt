package com.epam.valkaryne.geolocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class GeoViewModel : ViewModel() {

    val targetLatLng: MutableLiveData<LatLng> = MutableLiveData()
}

