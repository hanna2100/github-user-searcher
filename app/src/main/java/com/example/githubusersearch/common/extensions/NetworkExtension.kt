package com.example.githubusersearch.common.extensions

import com.example.githubusersearch.business.domain.model.NetworkError
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

suspend fun<T, R> Response<T>.subscribe(
    onSuccess: suspend (T) -> R,
    onError: suspend (NetworkError) -> R,
    onFailure: suspend (HttpException) -> R
):R {
    val is400Error = code()/100 == 4 // 400, 404, 405 등등 모든 400번대 에러 포함

    return when {
        isSuccessful -> {
            onSuccess(body()!!)
        }
        is400Error -> {
            val error = Gson().fromJson(errorBody()!!.string(), NetworkError::class.java)
            onError(error)
        }
        else -> {
            onFailure(HttpException(this))
        }
    }
}