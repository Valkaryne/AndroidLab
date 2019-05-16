package com.epam.valkaryne.intentmulti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * [MainActivity] is used as a container for [DownloadImageFragment]
 *
 *  @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DownloadImageFragment())
            .commit()
    }
}
