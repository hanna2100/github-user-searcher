package com.example.githubusersearch.business.domain.model

data class NetworkError (
    val message: String,
    val errors: List<ErrorDetail>,
    val documentation_url: String
)

data class ErrorDetail(
    val resource: String,
    val field: String,
    val code: String,
)