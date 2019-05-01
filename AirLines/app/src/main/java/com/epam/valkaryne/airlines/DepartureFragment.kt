package com.epam.valkaryne.airlines

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import com.epam.valkaryne.airlines.utils.FlightData

/**
 * An abstract [Fragment] class to display airlines data.
 *
 * @author Valentine Litvin
 */

abstract class DepartureFragment : Fragment() {

    var themeInterface: ThemeInterface? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillDepartData(arguments?.getParcelable(KEY_DEPART_DATA))
        fillReturnData(arguments?.getParcelable(KEY_RETURN_DATA))

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            themeInterface?.switchNightMode()
        }
    }

    private fun fillDepartData(departData: FlightData?) {
        departData?.let {
            val tvDepartDeppoint = view?.findViewById<TextView>(R.id.tv_depart_deppoint)
            tvDepartDeppoint?.text = it.depart
            val tvDepartArrpoint = view?.findViewById<TextView>(R.id.tv_depart_arrpoint)
            tvDepartArrpoint?.text = it.arrival
            val tvDepartDate = view?.findViewById<TextView>(R.id.tv_depart_date)
            tvDepartDate?.text = it.date
            val tvDepartPrice = view?.findViewById<TextView>(R.id.tv_depart_price)
            tvDepartPrice?.text = String.format(getString(R.string.price, it.price.toString(), it.currency))
            val tvDepartTakeoffTime = view?.findViewById<TextView>(R.id.tv_depart_takeoff_time)
            tvDepartTakeoffTime?.text = it.takeoffTime
            val tvDepartLandingTime = view?.findViewById<TextView>(R.id.tv_depart_landing_time)
            tvDepartLandingTime?.text = it.landingTime
            val tvDepartDuration = view?.findViewById<TextView>(R.id.tv_depart_duration)
            tvDepartDuration?.text = it.duration
            val tvDepartSeats = view?.findViewById<TextView>(R.id.tv_depart_seats)
            tvDepartSeats?.text = String.format(getString(R.string.free_seats, it.freeSeats.toString()))
        }
    }

    private fun fillReturnData(returnData: FlightData?) {
        returnData?.let {
            val tvReturnDeppoint = view?.findViewById<TextView>(R.id.tv_return_deppoint)
            tvReturnDeppoint?.text = it.depart
            val tvReturnArrpoint = view?.findViewById<TextView>(R.id.tv_return_arrpoint)
            tvReturnArrpoint?.text = it.arrival
            val tvReturnDate = view?.findViewById<TextView>(R.id.tv_return_date)
            tvReturnDate?.text = it.date
            val tvReturnPrice = view?.findViewById<TextView>(R.id.tv_return_price)
            tvReturnPrice?.text = String.format(getString(R.string.price, it.price.toString(), it.currency))
            val tvReturnTakeoffTime = view?.findViewById<TextView>(R.id.tv_return_takeoff_time)
            tvReturnTakeoffTime?.text = it.takeoffTime
            val tvReturnLandingTime = view?.findViewById<TextView>(R.id.tv_return_landing_time)
            tvReturnLandingTime?.text = it.landingTime
            val tvReturnDuration = view?.findViewById<TextView>(R.id.tv_return_duration)
            tvReturnDuration?.text = it.duration
            val tvReturnSeats = view?.findViewById<TextView>(R.id.tv_return_seats)
            tvReturnSeats?.text = String.format(getString(R.string.free_seats, it.freeSeats.toString()))
        }
    }

    companion object {
        const val KEY_DEPART_DATA = "depart"
        const val KEY_RETURN_DATA = "return"
    }
}