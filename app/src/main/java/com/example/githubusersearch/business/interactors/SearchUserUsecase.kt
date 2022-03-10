package com.example.githubusersearch.business.interactors

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class SearchUserUsecase (
    private val githubRetrofitService: GithubRetrofitService,
    private val userDefaultInfoMapper: UserDefaultInfoMapper,
) {

    suspend fun execute(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Response<List<User>> {
        val response = githubRetrofitService.searchUsers(query, sort, order, perPage, page)
        return if(response.isSuccessful) {
            val userDtoList = response.body()!!.users
            val userList = userDtoList.map { userDefaultInfoMapper.mapFromEntity(it) }
            Response.success(userList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}