package com.epam.valkaryne.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Main activity contains fragment with basic UI.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AlarmFragment())
            .commit()
    }
}
