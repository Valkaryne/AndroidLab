package com.epam.valkaryne.alarmclock

import android.content.Context
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import me.jfenn.slideactionview.SlideActionView

class AlarmActivity : AppCompatActivity() {

    private var vibrator: Vibrator? = null
    private var handler: Handler? = null
    private var vibrationRunnable = VibratorInfinite()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val slideView = findViewById<SlideActionView>(R.id.slideView)
        slideView.setRightIcon(VectorDrawableCompat.create(resources, R.drawable.ic_close, theme))
        slideView.setListener(SlideActionListener())

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        handler = Handler(Looper.getMainLooper())
        handler?.post(vibrationRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler?.removeCallbacks(vibrationRunnable)
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
