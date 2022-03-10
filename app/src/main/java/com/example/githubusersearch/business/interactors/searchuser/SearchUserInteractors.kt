package com.example.githubusersearch.business.interactors.searchuser

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.usecase.SearchUserUsecase
import com.example.githubusersearch.common.constant.SEARCH_USER_LIST_PER_PAGE
import retrofit2.Response

class SearchUserInteractors(
    private val searchUserUsecase: SearchUserUsecase
) {

    suspend fun searchUsers(
        query: String,
        sort: String,
        order: String,
        page: Int
    ): Response<List<User>> {
        return searchUserUsecase.execute(query, sort, order, SEARCH_USER_LIST_PER_PAGE, page)
    }
}