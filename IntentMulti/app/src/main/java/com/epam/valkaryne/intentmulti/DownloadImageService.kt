package com.epam.valkaryne.intentmulti

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    private var downloadedImage: Bitmap? = null

    private val localBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, createNotification())
    }

    override fun onHandleIntent(intent: Intent?) {
        val imgUrlResource = intent?.getStringExtra(IMAGE_URL_EXTRA)
        val imageUrl = URL(imgUrlResource)

        downloadedImage = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
        sendBroadcast(Intent(ACTION_COMPLETE))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return localBinder
    }

    fun getImage() = downloadedImage

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

    inner class LocalBinder : Binder() {
        fun getService() = this@DownloadImageService
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.channel"
        const val NOTIFICATION_CHANNEL_NAME = "IntentMulti"
        const val FOREGROUND_SERVICE_NOTIFICATION_ID = 546

        const val ACTION_COMPLETE = "${BuildConfig.APPLICATION_ID}.action.COMPLETE"

        const val IMAGE_URL_EXTRA = "image_url"
    }
}