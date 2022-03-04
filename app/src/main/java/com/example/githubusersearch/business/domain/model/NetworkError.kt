package com.example.githubusersearch.business.domain.model

data class NetworkError (
    val url: String,
    val status: Int,
    val message: String
)