package com.example.githubusersearch.business.interactors.searchuser

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.User

class SearchUserInteractors(
    private val githubDataSource: GithubDataSource
) {

    suspend fun searchUsers(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): List<User> {
        return githubDataSource.searchUsers(query, sort, order, perPage, page)
    }
}