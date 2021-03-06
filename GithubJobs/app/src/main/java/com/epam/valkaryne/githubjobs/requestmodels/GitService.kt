package com.epam.valkaryne.githubjobs.requestmodels

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Git retrofit interface to get list of jobs.
 *
 * @author Valentine Litvin
 */

interface GitService {

    @GET("/positions.json")
    fun getJobs(
        @Query("description") description: String,
        @Query("location") location: String
    ): Call<List<Job>>

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://jobs.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}