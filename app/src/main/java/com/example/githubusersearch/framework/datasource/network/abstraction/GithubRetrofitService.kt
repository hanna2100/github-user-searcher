package com.example.githubusersearch.framework.datasource.network.abstraction

import com.example.githubusersearch.framework.datasource.network.model.ContributorsDto
import com.example.githubusersearch.framework.datasource.network.model.RepositoryDetailDto
import com.example.githubusersearch.framework.datasource.network.model.RepositoryDto
import com.example.githubusersearch.framework.datasource.network.model.UserDetailInfoDto
import com.example.githubusersearch.framework.datasource.network.responose.UsersSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubRetrofitService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<UsersSearchResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") userName: String): Response<UserDetailInfoDto>

    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") userName: String): Response<List<RepositoryDto>>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepositoryDetailDto>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo")repo: String
    ): Response<List<ContributorsDto>>

}