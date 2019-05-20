package com.epam.valkaryne.githubjobs.requestmodels

import com.google.gson.annotations.SerializedName

/**
 * Data class for job object received from Git.
 *
 * @author Valentine Litvin
 */

data class Job(
    @SerializedName("title")
    val vacancy: String = "",
    val company: String = "",
    val location: String = "",
    @SerializedName("company_logo")
    val logoUrl: String = ""
)