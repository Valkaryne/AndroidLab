package com.epam.valkaryne.airlines

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epam.valkaryne.airlines.utils.FlightData
import kotlinx.android.synthetic.main.fragment_departure_constraint.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DepartureConstraintFragment : Fragment() {

    private var departData: FlightData? = null
    private var returnData: FlightData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departure_constraint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillDepartData()
        fillReturnData()
    }

    private fun fillDepartData() {
        departData?.let {
            tv_depart_deppoint.text = it.depart
            tv_depart_arrpoint.text = it.arrival
            tv_depart_date.text = it.date
            tv_depart_price.text = "${it.price} ${it.currency}"
            tv_depart_takeoff_time.text = it.takeoffTime
            tv_depart_landing_time.text = it.landingTime
            tv_depart_duration.text = it.duration
            tv_depart_seats.text = "Free seats: ${it.freeSeats}"
        }
    }

    private fun fillReturnData() {
        returnData?.let {
            tv_return_deppoint.text = it.depart
            tv_return_arrpoint.text = it.arrival
            tv_return_date.text = it.date
            tv_return_price.text = "${it.price} ${it.currency}"
            tv_return_takeoff_time.text = it.takeoffTime
            tv_return_landing_time.text = it.landingTime
            tv_return_duration.text = it.duration
            tv_return_seats.text = "Free seats: ${it.freeSeats}"
        }
    }

    companion object {
        fun newInstance(departData: FlightData, returnData: FlightData) : DepartureConstraintFragment {
            val fragment = DepartureConstraintFragment()

            fragment.departData = departData
            fragment.returnData = returnData

            return fragment
        }
    }
}
