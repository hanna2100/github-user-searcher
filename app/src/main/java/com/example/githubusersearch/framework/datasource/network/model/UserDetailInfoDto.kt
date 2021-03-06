package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class UserDetailInfoDto(

    @SerializedName("login")
    val login: String,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("blog")
    val blog: String?,

    @SerializedName("created_at")
    val createAt: String,


    )