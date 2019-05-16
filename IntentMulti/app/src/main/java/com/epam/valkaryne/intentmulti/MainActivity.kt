package com.epam.valkaryne.intentmulti

import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    private var bound = false
    private var sConn: ServiceConnection? = null
    private var serviceIntent: Intent? = null
    private var service: DownloadImageService? = null

    private var ivBig: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        serviceIntent = Intent(this, DownloadImageService::class.java)
        sConn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("SuperCat", "MainActivity onServiceDisconnected")
                bound = false
            }

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                Log.d("SuperCat", "MainActivity onServiceConnected")
                service = (binder as DownloadImageService.LocalBinder).getService()
                bound = true
            }
        }

        ivBig = findViewById(R.id.iv_big)
        if (viewModel?.bitmap != null) {
            ivBig?.setImageBitmap(viewModel?.bitmap)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab_download)
        fab.setOnClickListener {
            startService(serviceIntent)
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

    override fun onDestroy() {
        super.onDestroy()
        if (!bound) return
        sConn?.let {
            unbindService(it)
            bound = false
        }
    }

    private val onEvent = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("SuperCat", "Ready-Ready, Meow")
            viewModel?.bitmap = service?.getImage()
            ivBig?.setImageBitmap(viewModel?.bitmap)
        }
    }
}
