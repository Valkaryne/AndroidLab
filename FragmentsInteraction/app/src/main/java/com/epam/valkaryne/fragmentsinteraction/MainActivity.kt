package com.epam.valkaryne.fragmentsinteraction

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * The main Activity of the application. Its content depends on screen orientation.
 * In standard (portrait) mode it contains [AFragment] with <code>Button</code>; In landscape mode it contains [AFragment] with
 * <code>Button</code> and [BFragment] with <code>TextView</code>.
 * The main purpose of this application is counting of button clicks on [AFragment]
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity(), CountListener {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(KEY_COUNT)
        }

        when (getString(R.string.selected_configuration)) {
            CONFIG_STANDARD -> initFragmentsStandard()
            CONFIG_LANDSCAPE -> initFragmentsLandscape()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_COUNT, count)
    }

    override fun incrementCounter() {
        when (getString(R.string.selected_configuration)) {
            CONFIG_STANDARD -> incrementCounterStandard()
            CONFIG_LANDSCAPE -> incrementCounterLandscape()
        }
    }

    private fun initFragmentsStandard() {
        val mainFragment = AFragment.newInstance()
        replaceFragment(mainFragment, R.id.fragmentContainer)
    }

    private fun initFragmentsLandscape() {
        val firstFragment = AFragment.newInstance()
        replaceFragment(firstFragment, R.id.firstFragmentContainer)

        val secondFragment = BFragment.newInstance(count)
        replaceFragment(secondFragment, R.id.secondFragmentContainer)
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    private fun incrementCounterStandard() {
        val infoFragment = BFragment.newInstance(++count)
        replaceFragment(infoFragment, R.id.fragmentContainer)
    }

    private fun incrementCounterLandscape() {
        val secondFragment = supportFragmentManager.findFragmentById(R.id.secondFragmentContainer)
        if (secondFragment is BFragment)
            secondFragment.updateTextView(++count)
    }

    private companion object {
        const val CONFIG_STANDARD = "standard"
        const val CONFIG_LANDSCAPE = "landscape"
        const val KEY_COUNT = "count"
    }
}