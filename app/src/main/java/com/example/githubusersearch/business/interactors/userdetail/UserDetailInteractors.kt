package com.example.githubusersearch.business.interactors.userdetail

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.User
import retrofit2.Response

class UserDetailInteractors(
    private val githubDataSource: GithubDataSource
) {

    suspend fun getUser(userName: String): Response<User> {
        return githubDataSource.getUser(userName)
    }

    suspend fun getRepositories(userName: String): Response<List<Repository>> {
        return githubDataSource.getRepositories(userName)
    }

    suspend fun getRepository(owner: String, repo: String): Response<Repository> {
        return githubDataSource.getRepository(owner, repo)
    }

    suspend fun getContributors(owner: String, repo: String): Response<List<Repository.Contributor>> {
        return githubDataSource.getContributors(owner, repo)
    }
}