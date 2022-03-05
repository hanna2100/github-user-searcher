package com.example.githubusersearch.business.interactors.searchuser

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.common.constant.SEARCH_USER_LIST_PER_PAGE
import kotlinx.coroutines.delay
import retrofit2.Response

class SearchUserInteractors(
    private val githubDataSource: GithubDataSource
) {

    suspend fun searchUsers(
        query: String,
        sort: String,
        order: String,
        page: Int
    ): Response<List<User>> {
        return githubDataSource.searchUsers(query, sort, order, SEARCH_USER_LIST_PER_PAGE, page)
    }
}