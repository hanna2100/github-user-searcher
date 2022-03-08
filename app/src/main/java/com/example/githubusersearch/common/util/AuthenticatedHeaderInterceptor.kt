package com.example.githubusersearch.common.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatedHeaderInterceptor(private val acceptToken: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainRequest = chain.request()
        val request = chainRequest.newBuilder().apply {
            addHeader("accept", acceptToken)
        }.build()

        return chain.proceed(request)
    }
}