package com.example.githubusersearch.business.interactors.usecase

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class GetRepositoriesUsecase(
    private val githubRetrofitService: GithubRetrofitService,
    private val repositoryMapper: RepositoryMapper
) {
    suspend fun execute(userName: String): Response<List<Repository>> {
        val response = githubRetrofitService.getRepositories(userName)
        return if (response.isSuccessful) {
            val repositoryDtoList = response.body()!!
            val repositoryList = repositoryDtoList.map { repositoryMapper.mapFromEntity(it) }
            Response.success(repositoryList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}