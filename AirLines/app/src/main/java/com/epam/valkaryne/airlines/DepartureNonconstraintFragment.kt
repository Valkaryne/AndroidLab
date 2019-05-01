package com.epam.valkaryne.airlines

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epam.valkaryne.airlines.utils.FlightData

/**
 * A simple [Fragment] subclass. Based on Linear and Relative Layouts to display [FlightData] for airlines.
 * Has one Floating Action Button displays switches night mode.
 *
 * @author Valentine Litvin
 */
class DepartureNonconstraintFragment : DepartureFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_departure_nonconstraint, container, false)
    }

    companion object {

        fun newInstance(departData: FlightData, returnData: FlightData, themeInterface: ThemeInterface) : DepartureNonconstraintFragment {
            val fragment = DepartureNonconstraintFragment()

            val args = Bundle()
            args.putParcelable(KEY_DEPART_DATA, departData)
            args.putParcelable(KEY_RETURN_DATA, returnData)
            fragment.arguments = args
            fragment.themeInterface = themeInterface

            return fragment
        }
    }
}
