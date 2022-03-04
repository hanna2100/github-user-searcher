package com.example.githubusersearch.framework.datasource.network.abstraction

import com.example.githubusersearch.framework.datasource.network.responose.UsersSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRetrofitService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): UsersSearchResponse

}