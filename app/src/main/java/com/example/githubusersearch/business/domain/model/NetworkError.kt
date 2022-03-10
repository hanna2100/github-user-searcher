package com.example.githubusersearch.business.domain.model

data class NetworkError (
    val code: Int?,
    val message: String?,
    val errors: List<ErrorDetail>?,
    val documentation_url: String?
) {
    companion object {
        fun NetworkError.setCode(c: Int):NetworkError {
            return this.copy(code = c)
        }
    }
}

data class ErrorDetail(
    val resource: String,
    val field: String,
    val code: String,
)