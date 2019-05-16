package com.epam.valkaryne.intentmulti

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * [MainActivity] contains:
 *  - ImageView as a container for large bitmap image
 *  - FloatingActionButton initializes downloading of large bitmap image
 *  - ProgressBar is visible during the process of downloading
 *
 *  @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    private var bound = false
    private var sConn: ServiceConnection? = null
    private var serviceIntent: Intent? = null
    private var service: DownloadImageService? = null

    private var ivBig: ImageView? = null
    private var progressBarLoading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        serviceIntent = Intent(this, DownloadImageService::class.java)
        serviceIntent?.putExtra(DownloadImageService.IMAGE_URL_EXTRA, getString(R.string.big_image_url))

        sConn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                bound = false
            }

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                service = (binder as DownloadImageService.LocalBinder).getService()
                bound = true
            }
        }

        progressBarLoading = findViewById(R.id.progressbar_loading)
        if (viewModel?.isDownloading!!) {
            progressBarLoading?.visibility = View.VISIBLE
        }

        ivBig = findViewById(R.id.iv_big)
        if (viewModel?.bitmap != null) {
            ivBig?.setImageBitmap(viewModel?.bitmap)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab_download)
        fab.setOnClickListener {
            startService(serviceIntent)
            launchProgressBar()
        }
    }

    override fun onStart() {
        super.onStart()
        sConn?.let {
            bindService(serviceIntent, it, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(DownloadImageService.ACTION_COMPLETE)
        registerReceiver(onEvent, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(onEvent)
    }

    override fun onStop() {
        super.onStop()
        if (!bound) return
        sConn?.let {
            unbindService(it)
            bound = false
        }
    }

    private fun launchProgressBar() {
        viewModel?.isDownloading = true
        progressBarLoading?.visibility = View.VISIBLE
    }

    private fun dismissProgressBar() {
        viewModel?.isDownloading = false
        progressBarLoading?.visibility = View.GONE
    }

    private val onEvent = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            dismissProgressBar()
            viewModel?.bitmap = service?.getImage()
            ivBig?.setImageBitmap(viewModel?.bitmap)
        }
    }
}
