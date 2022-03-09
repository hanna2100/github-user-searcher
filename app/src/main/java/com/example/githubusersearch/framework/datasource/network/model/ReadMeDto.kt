package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class ReadMeDto (
    @SerializedName("content")
    val content: String,
)