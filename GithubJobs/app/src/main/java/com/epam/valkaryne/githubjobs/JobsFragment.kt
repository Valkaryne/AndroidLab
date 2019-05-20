package com.epam.valkaryne.githubjobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class JobsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val jobsRecycler = inflater.inflate(R.layout.fragment_jobs, container, false) as RecyclerView


        return jobsRecycler
    }


}