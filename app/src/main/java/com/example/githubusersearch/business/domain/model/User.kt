package com.example.githubusersearch.business.domain.model

data class User(
    val defaultInfo: DefaultInfo,
    val detailInfo: DetailInfo? = null,
)

data class DefaultInfo(
    val login: String,
    val id: Int,
    val avatarUrl: String,
)

data class DetailInfo(
    val followers: Int,
    val following: Int,
    val reposUrl: String,
    val name: String,
    val location: String,
    val email: String
)