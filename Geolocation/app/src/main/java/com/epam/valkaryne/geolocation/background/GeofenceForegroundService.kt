package com.epam.valkaryne.geolocation.background

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.epam.valkaryne.geolocation.R
import com.epam.valkaryne.geolocation.utils.FOREGROUND_SERVICE_NOTIFICATION_ID
import com.epam.valkaryne.geolocation.utils.createNotification
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Service launches Geofencing Client that tracks user position relative to desired area.
 * Able to work in background.
 *
 * @author Valentine Litvin
 */

class GeofenceForegroundService : Service() {

    private var started = false
    private var geofencingClient: GeofencingClient? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(
            FOREGROUND_SERVICE_NOTIFICATION_ID, createNotification(
                this, getString(R.string.foreground_service_message)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        removeGeofence()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!started) {
            geofencingClient = LocationServices.getGeofencingClient(this)
            val latLng = intent?.extras?.get(LAT_LNG_EXTRA) as LatLng
            addGeofence(latLng)
        }
        started = true
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(latLng: LatLng) {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val geofence = buildGeofence(latLng)
        geofencingClient?.addGeofences(buildGeofencingRequest(geofence), pendingIntent)
    }

    private fun removeGeofence() {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        geofencingClient?.removeGeofences(pendingIntent)
    }

    private fun buildGeofence(latLng: LatLng) =
        Geofence.Builder().setRequestId(RQS_GEOFENCE)
            .setCircularRegion(latLng.latitude, latLng.longitude, STANDARD_RADIUS)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

    private fun buildGeofencingRequest(geofence: Geofence) =
        GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()

    companion object {
        const val RQS_GEOFENCE = "gf_0"
        const val STANDARD_RADIUS = 100F

        const val LAT_LNG_EXTRA = "lat_lng"
    }
}