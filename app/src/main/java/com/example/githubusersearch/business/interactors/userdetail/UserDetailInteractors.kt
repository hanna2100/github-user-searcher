package com.example.githubusersearch.business.interactors.userdetail

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.User
import retrofit2.Response

class UserDetailInteractors(
    private val githubDataSource: GithubDataSource
) {

    suspend fun getUser(userName: String): Response<User> {
        return githubDataSource.getUser(userName)
    }
}