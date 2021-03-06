package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class UserDefaultInfoDto(
    @SerializedName("login")
    val login: String,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
