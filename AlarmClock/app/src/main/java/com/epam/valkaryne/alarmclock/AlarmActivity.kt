package com.epam.valkaryne.alarmclock

import android.content.Context
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import me.jfenn.slideactionview.SlideActionView

class AlarmActivity : AppCompatActivity() {

    private var vibrator: Vibrator? = null
    private var handler: Handler? = null
    private var vibrationRunnable = VibratorInfinite()

    private var ringtonePlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val slideView = findViewById<SlideActionView>(R.id.slideView)
        slideView.setLeftIcon(getDrawable(R.drawable.ic_close))
        slideView.setRightIcon(getDrawable(R.drawable.ic_close))
        slideView.setListener(SlideActionListener())

        ringtonePlayer = MediaPlayer.create(this, R.raw.ringtone)
        ringtonePlayer?.start()

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        handler = Handler(Looper.getMainLooper())
        handler?.post(vibrationRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler?.removeCallbacks(vibrationRunnable)
        ringtonePlayer?.stop()
    }

    inner class VibratorInfinite : Runnable {
        override fun run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            else
                vibrator?.vibrate(500)
            handler?.postDelayed(this, 1000)
        }
    }

    inner class SlideActionListener : me.jfenn.slideactionview.SlideActionListener {
        override fun onSlideLeft() {
            finish()
        }

        override fun onSlideRight() {
            finish()
        }

    }


}
