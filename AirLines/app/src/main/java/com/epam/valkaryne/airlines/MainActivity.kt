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

        val departData = DataManager.departFlightData()
        val returnData = DataManager.returnFlightData()

        btn_show_constraint.setOnClickListener { replaceFragment(DepartureConstraintFragment.newInstance(departData, returnData)) }
        btn_show_nonconstraint.setOnClickListener { replaceFragment(DepartureNonconstraintFragment.newInstance(departData, returnData)) }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    //TODO: separate margins in layouts
    //TODO: delete redundant resources and get rid of synthetic
    //TODO: make documentation
    //TODO: prey
}
