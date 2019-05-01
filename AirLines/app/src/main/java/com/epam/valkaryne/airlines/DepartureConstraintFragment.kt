package com.epam.valkaryne.airlines

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epam.valkaryne.airlines.utils.FlightData

/**
 * A simple [Fragment] subclass. Based on ConstraintLayout to display [FlightData] for airlines.
 * Has one Floating Action Button switches night mode.
 *
 * @author Valentine Litvin
 */
class DepartureConstraintFragment : DepartureFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_departure_constraint, container, false)
    }

    companion object {

        fun newInstance(departData: FlightData, returnData: FlightData, themeInterface: ThemeInterface) : DepartureConstraintFragment {
            val fragment = DepartureConstraintFragment()

            val args = Bundle()
            args.putParcelable(KEY_DEPART_DATA, departData)
            args.putParcelable(KEY_RETURN_DATA, returnData)
            fragment.arguments = args
            fragment.themeInterface = themeInterface

            return fragment
        }
    }
}
