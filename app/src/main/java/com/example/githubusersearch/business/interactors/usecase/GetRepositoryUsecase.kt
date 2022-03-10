package com.example.githubusersearch.business.interactors.usecase

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryDetailMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class GetRepositoryUsecase (
    private val githubRetrofitService: GithubRetrofitService,
    private val repositoryDetailMapper: RepositoryDetailMapper,
) {
    suspend fun execute(owner: String, repo: String): Response<Repository> {
        val response = githubRetrofitService.getRepository(owner, repo)
        return if (response.isSuccessful) {
            val repositoryDetailDto = response.body()!!
            Response.success(repositoryDetailMapper.mapFromEntity(repositoryDetailDto))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}