package com.epam.valkaryne.githubjobs

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("title")
    val vacancy: String = "",
    val company: String = "",
    val location: String = "",
    @SerializedName("company_logo")
    val logoUrl: String = ""
)