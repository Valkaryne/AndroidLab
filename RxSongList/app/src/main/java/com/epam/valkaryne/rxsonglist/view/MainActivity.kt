package com.epam.valkaryne.rxsonglist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.valkaryne.rxsonglist.R
import com.epam.valkaryne.rxsonglist.viewmodel.SongsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SongsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SongsViewModel::class.java)
        savedInstanceState ?: viewModel.initialize()

        val recycler = findViewById<RecyclerView>(R.id.recycler_songs)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = viewModel.adapter
    }
}
