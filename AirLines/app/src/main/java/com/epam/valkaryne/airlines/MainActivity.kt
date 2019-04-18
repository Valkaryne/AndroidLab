package com.epam.valkaryne.airlines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.epam.valkaryne.airlines.utils.FlightData
import com.epam.valkaryne.airlines.utils.FlightTime
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

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
        return FlightData(
            "MSQ", "FLO",
            LocalDate.of(2019, 9, 16),
            435, FlightTime(0, 20),
            FlightTime(9, 20), 3)
    }

    private fun returnFlightData() : FlightData {
        return FlightData(
            "FLO", "MSQ",
            LocalDate.of(2019, 9, 17),
            488, FlightTime(5, 10),
            FlightTime(9, 20),5)
    }

    //TODO: separate class for FlightDataGenerator
    //TODO: separate margins in layouts
    //TODO: delete redundant resources and get rid of synthetic
    //TODO: make documentation
    //TODO: prey
}
