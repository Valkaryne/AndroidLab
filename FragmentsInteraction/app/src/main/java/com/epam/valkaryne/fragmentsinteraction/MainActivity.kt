package com.epam.valkaryne.fragmentsinteraction

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
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
    private var orientation = Configuration.ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(KEY_COUNT)
        }

        orientation = resources.configuration.orientation

        when (orientation) {
            CONFIG_PORTRAIT -> initFragmentsStandard()
            CONFIG_LANDSCAPE -> initFragmentsLandscape()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_COUNT, count)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (orientation == CONFIG_LANDSCAPE)
            finish()
    }

    override fun incrementCounter() {
        when (orientation) {
            CONFIG_PORTRAIT -> incrementCounterStandard()
            CONFIG_LANDSCAPE -> incrementCounterLandscape()
        }
    }

    private fun initFragmentsStandard() {
        val mainFragment = AFragment.newInstance()
        replaceFragment(mainFragment, R.id.fragmentContainer, FRAGMENT_A)

        val index = supportFragmentManager.backStackEntryCount - 1
        if (index > -1) {
            checkLoadFragmentB(index)
        }
    }

    private fun checkLoadFragmentB(index: Int) {
        val backStack = supportFragmentManager.getBackStackEntryAt(index)
        val tag = backStack.name
        if (tag == FRAGMENT_B) {
            val infoFragment = BFragment.newInstance(count)
            replaceFragment(infoFragment, R.id.fragmentContainer, FRAGMENT_B)
        }
    }

    private fun initFragmentsLandscape() {
        val firstFragment = AFragment.newInstance()
        replaceFragment(firstFragment, R.id.firstFragmentContainer, FRAGMENT_A)

        val secondFragment = BFragment.newInstance(count)
        replaceFragment(secondFragment, R.id.secondFragmentContainer, FRAGMENT_B)
    }

    private fun replaceFragment(fragment: Fragment, frameId: Int, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        checkAddToBackStack(fragment, tag, transaction)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    private fun checkAddToBackStack(fragment: Fragment, tag: String?, transaction: FragmentTransaction) {
        if (fragment !is AFragment && orientation == CONFIG_PORTRAIT) {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction.addToBackStack(tag)
        }
    }

    private fun incrementCounterStandard() {
        val infoFragment = BFragment.newInstance(++count)
        replaceFragment(infoFragment, R.id.fragmentContainer, FRAGMENT_B)
    }

    private fun incrementCounterLandscape() {
        val secondFragment = supportFragmentManager.findFragmentById(R.id.secondFragmentContainer)
        if (secondFragment is BFragment)
            secondFragment.updateTextView(++count)
    }

    private companion object {
        const val CONFIG_PORTRAIT = Configuration.ORIENTATION_PORTRAIT
        const val CONFIG_LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE
        const val FRAGMENT_A = "AFragment"
        const val FRAGMENT_B = "BFragment"
        const val KEY_COUNT = "count"
    }
}