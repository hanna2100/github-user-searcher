package com.example.githubusersearch.business.interactors.usecase

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.framework.datasource.network.mappers.ContributorsMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class GetContributorsUsecase (
    private val githubRetrofitService: GithubRetrofitService,
    private val contributorsMapper: ContributorsMapper
) {
    suspend fun execute(owner: String, repo: String): Response<List<Repository.Contributor>> {
        val response = githubRetrofitService.getContributors(owner, repo)
        return if (response.isSuccessful) {
            val contributorsDtoList = response.body()?: emptyList()
            val contributorsDomainList = contributorsDtoList.map { contributorsMapper.mapFromEntity(it) }
            Response.success(contributorsDomainList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}