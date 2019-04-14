package com.epam.valkaryne.fragmentsinteraction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b.*

/**
 * A simple [Fragment] subclass. It contains only one <code>TextView</code> which displays the value of the click-counter.
 *
 * @author Valentine Litvin
 */
class BFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onStart() {
        super.onStart()
        val count = arguments?.getInt(KEY_COUNT)
        count?.let { updateTextView(it) }
    }

    fun updateTextView(count: Int) {
        tvCounter.text = String.format(getString(R.string.counter_text, count.toString()))
    }

    companion object {
        const val KEY_COUNT = "count"

        fun newInstance(count: Int) : BFragment {
            val fragment = BFragment()

            val args = Bundle()
            args.putInt(KEY_COUNT, count)
            fragment.arguments = args

            return fragment
        }
    }

}
