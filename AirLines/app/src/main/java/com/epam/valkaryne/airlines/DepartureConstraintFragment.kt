package com.epam.valkaryne.airlines

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_departure_constraint.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DepartureConstraintFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departure_constraint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val departPointDep = arguments?.getString("${FlightData.DEPART_POINT}${FlightData.DEPART_MOD}")
        val arrivalPointDep = arguments?.getString("${FlightData.ARRIVAL_POINT}${FlightData.DEPART_MOD}")
        val dateDep = arguments?.getString("${FlightData.DATE}${FlightData.DEPART_MOD}")
        val priceDep = arguments?.getInt("${FlightData.PRICE}${FlightData.DEPART_MOD}")
        val currencyDep = arguments?.getString("${FlightData.CURRENCY}${FlightData.DEPART_MOD}")
        val takeoffDep = arguments?.getString("${FlightData.TAKEOFF_TIME}${FlightData.DEPART_MOD}")
        val landingDep = arguments?.getString("${FlightData.LANDING_TIME}${FlightData.DEPART_MOD}")
        val durationDep = arguments?.getString("${FlightData.DURATION}${FlightData.DEPART_MOD}")
        val freeDep = arguments?.getInt("${FlightData.FREE_SEATS}${FlightData.DEPART_MOD}")

        val departPointRet = arguments?.getString("${FlightData.DEPART_POINT}${FlightData.RETURN_MOD}")
        val arrivalPointRet = arguments?.getString("${FlightData.ARRIVAL_POINT}${FlightData.RETURN_MOD}")
        val dateRet = arguments?.getString("${FlightData.DATE}${FlightData.RETURN_MOD}")
        val priceRet = arguments?.getInt("${FlightData.PRICE}${FlightData.RETURN_MOD}")
        val currencyRet = arguments?.getString("${FlightData.CURRENCY}${FlightData.RETURN_MOD}")
        val takeoffRet = arguments?.getString("${FlightData.TAKEOFF_TIME}${FlightData.RETURN_MOD}")
        val landingRet = arguments?.getString("${FlightData.LANDING_TIME}${FlightData.RETURN_MOD}")
        val durationRet = arguments?.getString("${FlightData.DURATION}${FlightData.RETURN_MOD}")
        val freeRet = arguments?.getInt("${FlightData.FREE_SEATS}${FlightData.RETURN_MOD}")

        tv_depart_date.text = dateDep
        tv_depart_seats.text = "Frees seats: $freeDep"
        tv_depart_price.text = "$priceDep $currencyDep"
        tv_depart_deppoint.text = departPointDep
        tv_depart_arrpoint.text = arrivalPointDep
        tv_depart_takeoff_time.text = takeoffDep
        tv_depart_landing_time.text = landingDep
        tv_depart_duration.text = durationDep

        tv_return_date.text = dateRet
        tv_return_seats.text = "Frees seats: $freeRet"
        tv_return_price.text = "$priceRet $currencyRet"
        tv_return_deppoint.text = departPointRet
        tv_return_arrpoint.text = arrivalPointRet
        tv_return_takeoff_time.text = takeoffRet
        tv_return_landing_time.text = landingRet
        tv_return_duration.text = durationRet
    }

    companion object {
        fun newInstance(departData: FlightData, returnData: FlightData) : DepartureConstraintFragment {
            val fragment = DepartureConstraintFragment()

            val args = Bundle()
            args.putString("${FlightData.DEPART_POINT}${FlightData.DEPART_MOD}", departData.departPoint)
            args.putString("${FlightData.ARRIVAL_POINT}${FlightData.DEPART_MOD}", departData.arrivalPoint)
            args.putString("${FlightData.DATE}${FlightData.DEPART_MOD}", departData.date)
            args.putInt("${FlightData.PRICE}${FlightData.DEPART_MOD}", departData.price)
            args.putString("${FlightData.CURRENCY}${FlightData.DEPART_MOD}", departData.currency)
            args.putString("${FlightData.TAKEOFF_TIME}${FlightData.DEPART_MOD}", departData.takeoffTime)
            args.putString("${FlightData.LANDING_TIME}${FlightData.DEPART_MOD}", departData.landingTime)
            args.putString("${FlightData.DURATION}${FlightData.DEPART_MOD}", departData.duration)
            args.putInt("${FlightData.FREE_SEATS}${FlightData.DEPART_MOD}", departData.freeSeats)

            args.putString("${FlightData.DEPART_POINT}${FlightData.RETURN_MOD}", returnData.departPoint)
            args.putString("${FlightData.ARRIVAL_POINT}${FlightData.RETURN_MOD}", returnData.arrivalPoint)
            args.putString("${FlightData.DATE}${FlightData.RETURN_MOD}", returnData.date)
            args.putInt("${FlightData.PRICE}${FlightData.RETURN_MOD}", returnData.price)
            args.putString("${FlightData.CURRENCY}${FlightData.RETURN_MOD}", returnData.currency)
            args.putString("${FlightData.TAKEOFF_TIME}${FlightData.RETURN_MOD}", returnData.takeoffTime)
            args.putString("${FlightData.LANDING_TIME}${FlightData.RETURN_MOD}", returnData.landingTime)
            args.putString("${FlightData.DURATION}${FlightData.RETURN_MOD}", returnData.duration)
            args.putInt("${FlightData.FREE_SEATS}${FlightData.RETURN_MOD}", returnData.freeSeats)

            fragment.arguments = args

            return fragment
        }
    }
}
