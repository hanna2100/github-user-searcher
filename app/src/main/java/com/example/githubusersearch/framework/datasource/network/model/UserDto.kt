package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("login")
    val login: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String,

)
