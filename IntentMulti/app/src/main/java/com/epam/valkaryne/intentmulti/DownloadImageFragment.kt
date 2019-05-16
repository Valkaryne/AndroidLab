package com.epam.valkaryne.intentmulti

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Main Fragment contains:
 *  - ImageView as a container for large bitmap image
 *  - FloatingActionButton initializes downloading of large bitmap image
 *  - ProgressBar is visible during the process of downloading
 *
 * @author Valentine Litvin
 */

class DownloadImageFragment : Fragment() {

    private var viewModel: MainViewModel? = null

    private var serviceIntent: Intent? = null
    private var service: DownloadImageService? = null
    private val sConn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            viewModel?.isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as DownloadImageService.LocalBinder).getService()
            viewModel?.isBound = true
        }
    }

    private var ivBig: ImageView? = null
    private var progressBarLoading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        serviceIntent = Intent(activity, DownloadImageService::class.java)
        serviceIntent?.putExtra(
            DownloadImageService.IMAGE_URL_EXTRA,
            getString(R.string.big_image_url)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBarLoading = view.findViewById(R.id.progressbar_loading)
        if (viewModel?.isDownloading!!) {
            progressBarLoading?.visibility = View.VISIBLE
            activity?.startService(serviceIntent)
        }

        ivBig = view.findViewById(R.id.iv_big)
        if (viewModel?.bitmap != null) {
            ivBig?.setImageBitmap(viewModel?.bitmap)
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_download)
        fab.setOnClickListener {
            activity?.bindService(serviceIntent, sConn, Context.BIND_AUTO_CREATE)
            activity?.startService(serviceIntent)
            launchProgressBar()
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.bindService(serviceIntent, sConn, 0)
    }

    override fun onResume() {
        super.onResume()
        val actionCompleteFilter = IntentFilter(DownloadImageService.ACTION_COMPLETE)
        activity?.registerReceiver(onCompleteEvent, actionCompleteFilter)
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(onCompleteEvent)
    }

    override fun onStop() {
        super.onStop()
        if (!viewModel?.isBound!!) return
        activity?.unbindService(sConn)
        viewModel?.isBound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.stopService(serviceIntent)
    }

    private fun launchProgressBar() {
        viewModel?.isDownloading = true
        progressBarLoading?.visibility = View.VISIBLE
    }

    private fun dismissProgressBar() {
        viewModel?.isDownloading = false
        progressBarLoading?.visibility = View.GONE
    }

    private val onCompleteEvent = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.extras?.getInt(DownloadImageService.COMPLETE_FLAG_EXTRA)
            viewModel?.bitmap = service?.getImage()
            ivBig?.setImageBitmap(viewModel?.bitmap)

            if (result != DownloadImageService.COMPLETE_FLAG_FAILURE) {
                dismissProgressBar()
                activity?.unbindService(sConn)
                activity?.stopService(serviceIntent)
            }
        }
    }
}