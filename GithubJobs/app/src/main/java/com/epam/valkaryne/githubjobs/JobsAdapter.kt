package com.epam.valkaryne.githubjobs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class JobsAdapter(private val jobs: List<Job>, private val context: Context?) :
    RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.card_job, parent, false) as CardView
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobs[position]
        val itemView = holder.item

        val tvVacancy = itemView.findViewById<TextView>(R.id.tv_card_vacancy)
        val tvCompany = itemView.findViewById<TextView>(R.id.tv_card_company)
        val tvLocation = itemView.findViewById<TextView>(R.id.tv_card_location)
        val ivLogo = itemView.findViewById<ImageView>(R.id.iv_card_logo)

        tvVacancy.text = job.vacancy
        tvCompany.text = job.company
        tvLocation.text = job.location

        context?.let {
            Glide.with(it)
                .load(job.logoUrl).apply(RequestOptions
                    .circleCropTransform()
                    .fitCenter())
                .error(R.drawable.ic_empty_logo)
                .into(ivLogo)
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    inner class ViewHolder(val item: CardView) : RecyclerView.ViewHolder(item)
}