package com.epam.valkaryne.airlines

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.widget.Button
import com.epam.valkaryne.airlines.utils.DataManager

/**
 * [MainActivity] class for layout demonstration. Has two buttons.
 * One [Button] shows the fragment based on ConstraintLayout.
 * The second [Button] shows the fragment base on Linear and Relative Layouts.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)

        val btnShowConstraint = findViewById<Button>(R.id.btn_show_constraint)
        val btnShowNonconstraint = findViewById<Button>(R.id.btn_show_nonconstraint)

        val departData = DataManager.departFlightData()
        val returnData = DataManager.returnFlightData()

        val themeSwitcher = ThemeSwitcher()
        val fragmentConstraint = DepartureConstraintFragment.newInstance(departData, returnData, themeSwitcher)
        val fragmentNonconstraint = DepartureNonconstraintFragment.newInstance(departData, returnData, themeSwitcher)

        if (savedInstanceState == null)
            replaceFragment(fragmentConstraint)

        btnShowConstraint.setOnClickListener {
            replaceFragment(fragmentConstraint)
        }

        btnShowNonconstraint.setOnClickListener {
            replaceFragment(fragmentNonconstraint)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val isConstraint = fragment is DepartureConstraintFragment

        outState?.putBoolean(KEY_LAYOUT_TYPE, isConstraint)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is DepartureFragment)
            fragment.themeInterface = ThemeSwitcher()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    inner class ThemeSwitcher : ThemeInterface {
        override fun switchNightMode() {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Configuration.UI_MODE_NIGHT_YES -> delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private companion object {
        const val KEY_LAYOUT_TYPE = "layout_type"
    }
}
