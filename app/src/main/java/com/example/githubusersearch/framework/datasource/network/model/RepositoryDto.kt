package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class RepositoryDto (
    @SerializedName("owner")
    val owner: RepositoryOwnerDto,

    @SerializedName("name")
    val name: String?,

    @SerializedName("full_name")
    val fullName: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("stargazers_count")
    val stargazersCount: Int,
)
