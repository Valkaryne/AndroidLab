package com.epam.valkaryne.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.epam.valkaryne.datastructures.R
import com.epam.valkaryne.utils.CustomStack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val stack = CustomStack<Double>()
    private val random = java.util.Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_display.text = if (stack.isEmpty()) "Stack is empty" else stack.toString()

        btn_push.setOnClickListener {
            stack.push(random.nextDouble())
            tv_display.text = stack.toString()
        }

        btn_pop.setOnClickListener{
            if (stack.isEmpty())
                tv_display.text = getString(R.string.pop_warning)
            else {
                stack.pop()
                tv_display.text = if (stack.isEmpty()) "Stack is empty" else stack.toString()
            }
        }
    }
}
