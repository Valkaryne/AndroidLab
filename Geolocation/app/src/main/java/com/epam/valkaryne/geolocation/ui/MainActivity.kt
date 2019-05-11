package com.epam.valkaryne.geolocation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.epam.valkaryne.geolocation.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(R.id.controls_container, ControlsFragment())
        setFragment(R.id.map_container, MapFragment())
    }

    private fun setFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}
