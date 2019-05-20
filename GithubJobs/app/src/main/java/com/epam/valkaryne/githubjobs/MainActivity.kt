package com.epam.valkaryne.githubjobs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * The simplest Activity contains fragment with recycler view.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, JobsFragment())
            .commit()
    }
}
