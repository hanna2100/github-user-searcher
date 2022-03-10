package com.example.githubusersearch.business.interactors.usecase

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.framework.datasource.network.mappers.UserDetailInfoMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class GetUserUsecase (
    private val githubRetrofitService: GithubRetrofitService,
    private val userDetailInfoMapper: UserDetailInfoMapper
) {
    suspend fun execute(userName: String): Response<User> {
        val response = githubRetrofitService.getUser(userName)
        return if(response.isSuccessful) {
            val userDetailInfoDto = response.body()!!
            Response.success(userDetailInfoMapper.mapFromEntity(userDetailInfoDto))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}