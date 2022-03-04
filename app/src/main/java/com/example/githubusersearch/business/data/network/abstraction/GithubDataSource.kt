package com.example.githubusersearch.business.data.network.abstraction

import com.example.githubusersearch.business.domain.model.User

interface GithubDataSource {

    suspend fun searchUsers(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): List<User>
}