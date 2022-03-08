package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerDto(
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
