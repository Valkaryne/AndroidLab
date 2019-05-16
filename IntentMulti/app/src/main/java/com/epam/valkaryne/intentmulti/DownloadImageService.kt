package com.epam.valkaryne.intentmulti

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.net.URL

/**
 * Intent service downloads large bitmap data from the internet and sends broadcast signal on ready.
 *
 * @author Valentine Litvin
 */

class DownloadImageService : IntentService("Image Downloader") {

    private var imageUrl: URL? = null
    private var downloadedImage: Bitmap? = null

    private val localBinder = LocalBinder()

    private val resultIntent = Intent(ACTION_COMPLETE)

    private var isDisconnected = false

    override fun onCreate() {
        super.onCreate()

        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, createNotification())
        val connectivityChangeFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(onConnectivityStateEvent, connectivityChangeFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onConnectivityStateEvent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onHandleIntent(intent: Intent?) {
        val imgUrlResource = intent?.getStringExtra(IMAGE_URL_EXTRA)
        imageUrl = URL(imgUrlResource)

        downloadImage()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return localBinder
    }

    fun getImage() = downloadedImage

    private fun downloadImage() {
        resultIntent.putExtra(COMPLETE_FLAG_EXTRA, COMPLETE_FLAG_SUCCESS)
        isDisconnected = false

        downloadedImage =
            BitmapFactory.decodeStream(imageUrl?.openConnection()?.getInputStream())

        sendBroadcast(resultIntent)
    }

    private fun createNotification(): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
        ) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(NOTIFICATION_CHANNEL_NAME)
            .setContentText(getString(R.string.image_downloading))
            .setProgress(100, 0, true)
            .build()
    }

    private val onConnectivityStateEvent = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (!isNetworkAvailable(context)) {
                isDisconnected = true
                resultIntent.putExtra(COMPLETE_FLAG_EXTRA, COMPLETE_FLAG_FAILURE)
            } else if (isNetworkAvailable(context) && isDisconnected) {
                Thread { downloadImage() }.start()
            }
        }

        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null && networkInfo.isConnected)
        }
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@DownloadImageService
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.channel"
        const val NOTIFICATION_CHANNEL_NAME = "IntentMulti"
        const val FOREGROUND_SERVICE_NOTIFICATION_ID = 546

        const val ACTION_COMPLETE = "${BuildConfig.APPLICATION_ID}.action.COMPLETE"
        const val COMPLETE_FLAG_SUCCESS = 340
        const val COMPLETE_FLAG_FAILURE = 341

        const val COMPLETE_FLAG_EXTRA = "action_result"
        const val IMAGE_URL_EXTRA = "image_url"
    }
}