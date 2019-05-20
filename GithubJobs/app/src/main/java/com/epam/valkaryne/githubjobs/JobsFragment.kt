package com.epam.valkaryne.githubjobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobsFragment : Fragment() {

    private var adapter: JobsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val jobsRecycler =
            inflater.inflate(R.layout.fragment_jobs, container, false) as RecyclerView

        adapter = JobsAdapter(getJobsFromGit(), context)
        jobsRecycler.adapter = adapter
        jobsRecycler.layoutManager = LinearLayoutManager(activity)

        return jobsRecycler
    }

    private fun getJobsFromGit(): List<Job> {
        val jobsList: MutableList<Job> = ArrayList()

        val description = getString(R.string.description_standard)
        val location = getString(R.string.location_standard)

        val service = GitService.retrofit.create(GitService::class.java)
        val call = service.getJobs(description, location)

        call.enqueue(object : Callback<List<Job>> {
            override fun onResponse(call: Call<List<Job>>, response: Response<List<Job>>) {
                response.body()?.let {
                    jobsList.addAll(it)
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                Toast.makeText(this@JobsFragment.context, "ERROR: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        return jobsList
    }
}