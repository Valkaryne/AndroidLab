package com.epam.valkaryne.fragmentsinteraction

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.*

/**
 * A simple [Fragment] subclass. It contains only one <code>Button</code> which handling depends on screen orientation.
 * In standard (portrait) mode it increments the counter and replaces this [Fragment] with [BFragment]; in landscape
 * mode it just increments the counter without fragment-replacing.
 *
 * @author Valentine Litvin
 */
class AFragment : Fragment() {

    private var listener: CountListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as CountListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener { listener?.incrementCounter() }
    }

    companion object {
        fun newInstance() : AFragment {
            return AFragment()
        }
    }
}
