package com.example.githubusersearch.business.data.network.implementation

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.UserDtoMapper
import com.example.githubusersearch.framework.datasource.network.responose.UsersSearchResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubDataSourceImpl
@Inject
constructor(
    private val githubRetrofitService: GithubRetrofitService,
    private val mapper: UserDtoMapper,
): GithubDataSource{

    override suspend fun searchUsers(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Response<List<User>> {

        val response = githubRetrofitService.searchUsers(query, sort, order, perPage, page)

        return if(response.isSuccessful) {
            val userDtoList = response.body()!!.users
            val userList = userDtoList.map { mapper.mapFromEntity(it) }
            Response.success(userList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

}