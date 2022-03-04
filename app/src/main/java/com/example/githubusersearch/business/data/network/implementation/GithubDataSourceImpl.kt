package com.example.githubusersearch.business.data.network.implementation

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.UserDtoMapper
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
    ): List<User> {
        val response = githubRetrofitService.searchUsers(query, sort, order, perPage, page)
        val users = response.users
        val list = users.map { mapper.mapFromEntity(it) }
        println("list size = ${list.size}")
        return users.map { mapper.mapFromEntity(it) }
    }

}