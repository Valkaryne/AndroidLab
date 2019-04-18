package com.epam.valkaryne.airlines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_show_constraint.setOnClickListener { replaceFragment(DepartureConstraintFragment.newInstance(departFlightData(), returnFlightData())) }
        btn_show_nonconstraint.setOnClickListener { replaceFragment(DepartureNonconstraintFragment.newInstance(departFlightData(), returnFlightData())) }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    private fun departFlightData() : FlightData {
        return FlightData("MSQ", "FLO", "16 SEP 2019", 435, "00:20",
            "09:20", "9:00", 3)
    }

    private fun returnFlightData() : FlightData {
        return FlightData("FLO", "MSQ", "17 SEP 2019", 488, "05:10",
            "09:20", "4:10", 5)
    }
}
