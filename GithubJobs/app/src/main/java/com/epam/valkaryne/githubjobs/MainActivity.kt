package com.epam.valkaryne.githubjobs

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * The simplest Activity contains fragment with recycler view.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    private var networkStatus: TextView? = null
    private var wasOffline = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkStatus = findViewById(R.id.tv_network_status)
        initNetworkCallback()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, JobsFragment())
            .commit()
    }

    private fun initNetworkCallback() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(
            request,
            SimpleNetworkCallback(connectivityManager)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            connectivityManager.requestNetwork(request, SimpleNetworkCallback(),
                1500)
    }

    inner class SimpleNetworkCallback() :
        ConnectivityManager.NetworkCallback() {

        private var manager: ConnectivityManager? = null

        constructor(manager: ConnectivityManager) : this() {
            this.manager = manager
        }

        override fun onUnavailable() {
            super.onUnavailable()
            setNetworkStateVisible(true)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            if (wasOffline) setupFragment()
            setNetworkStateVisible(false)
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
            if (manager?.getNetworkInfo(network) == null) {
                setNetworkStateVisible(true)
            }
        }

        private fun setNetworkStateVisible(isOffline: Boolean) {
            Handler(Looper.getMainLooper()).post {
                networkStatus?.visibility = if (isOffline) View.VISIBLE else View.GONE
            }
            wasOffline = isOffline
        }
    }
}
