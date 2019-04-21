package com.epam.valkaryne.airlines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Button

/**
 * [MainActivity] class for layout demonstration. Has two buttons.
 * One [Button] shows the fragment based on ConstraintLayout.
 * The second [Button] shows the fragment base on Linear and Relative Layouts.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    var isConstraint = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val departData = DataManager.departFlightData()
        val returnData = DataManager.returnFlightData()

        val btnShowConstraint = findViewById<Button>(R.id.btn_show_constraint)
        val btnShowNonconstraint = findViewById<Button>(R.id.btn_show_nonconstraint)

        if (savedInstanceState != null)
            isConstraint = savedInstanceState.getBoolean(KEY_LAYOUT_TYPE)

        replaceFragment(
            if (isConstraint) DepartureConstraintFragment.newInstance(departData, returnData)
            else DepartureNonconstraintFragment.newInstance(departData, returnData)
        )

        btnShowConstraint.setOnClickListener {
            replaceFragment(DepartureConstraintFragment.newInstance(departData, returnData))
        }

        btnShowNonconstraint.setOnClickListener {
            replaceFragment(DepartureNonconstraintFragment.newInstance(departData, returnData))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        isConstraint = fragment is DepartureConstraintFragment

        outState?.putBoolean(KEY_LAYOUT_TYPE, isConstraint)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    private companion object {
        const val KEY_LAYOUT_TYPE = "layout_type"
    }
}
