package com.epam.valkaryne.rxsonglist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.valkaryne.rxsonglist.R
import com.epam.valkaryne.rxsonglist.viewmodel.SongsViewModel
import com.jakewharton.rxbinding.widget.RxTextView

/**
 * The simplest [MainActivity] is considered as View contains [RecyclerView] with cards of songs
 * and [EditText] to filter them.
 *
 * @author Valentine Litvin
 */

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

        val etSearch = findViewById<EditText>(R.id.et_search)
        etSearch.setText(viewModel.query)

        RxTextView.afterTextChangeEvents(etSearch).subscribe { event ->
            viewModel.query = event.view().text.toString()
            viewModel.filterSongsInAdapter()
        }
    }
}
