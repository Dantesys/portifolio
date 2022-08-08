package com.dantesys.portifolio.data.model

import com.google.gson.annotations.SerializedName

data class Repo (
    val id: Long,
    val name: String,
    val owner: Owner,
    @SerializedName("html_url")
    val htmlURL: String,
    val description: String? = null,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    val language: String
)
