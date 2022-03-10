package com.example.githubusersearch.business.data.network.abstraction

import com.example.githubusersearch.business.domain.model.ReadMe
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.User
import retrofit2.Response

interface GithubDataSource {

    suspend fun getUser(userName: String): Response<User>

    suspend fun getRepositories(userName: String): Response<List<Repository>>

    suspend fun getRepository(owner: String, repo: String): Response<Repository>

    suspend fun getContributors(owner: String, repo: String): Response<List<Repository.Contributor>>

    suspend fun getReadMe(owner: String, repo: String): Response<ReadMe>

    suspend fun renderMarkDown(content: String): Response<Repository.MarkdownHTML>

}