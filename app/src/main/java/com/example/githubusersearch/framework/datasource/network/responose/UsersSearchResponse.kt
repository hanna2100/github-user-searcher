package com.example.githubusersearch.framework.datasource.network.responose

import com.example.githubusersearch.framework.datasource.network.model.UserDto
import com.google.gson.annotations.SerializedName

data class UsersSearchResponse(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val users: List<UserDto>
)
