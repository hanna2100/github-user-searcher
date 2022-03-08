package com.example.githubusersearch.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class RepositoryDetailDto (
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

    @SerializedName("description")
    val description: String?,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("pushed_at")
    val pushedAt: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

)