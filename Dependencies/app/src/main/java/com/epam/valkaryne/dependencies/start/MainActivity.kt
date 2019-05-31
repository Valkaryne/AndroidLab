package com.epam.valkaryne.dependencies.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epam.valkaryne.dependencies.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(Order())

        button.setOnClickListener { presenter.makeCoffee() }
    }
}
