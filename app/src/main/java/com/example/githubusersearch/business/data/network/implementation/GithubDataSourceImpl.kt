package com.example.githubusersearch.business.data.network.implementation

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryDetailMapper
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryMapper
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoDtoMapper
import com.example.githubusersearch.framework.datasource.network.mappers.UserDetailInfoDtoMapper
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubDataSourceImpl
@Inject
constructor(
    private val githubRetrofitService: GithubRetrofitService,
    private val mapperDefaultInfo: UserDefaultInfoDtoMapper,
    private val mapperDetailInfo: UserDetailInfoDtoMapper,
    private val mapperRepository: RepositoryMapper,
    private val mapperRepositoryDetailMapper: RepositoryDetailMapper,
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
            println(userDtoList)
            val userList = userDtoList.map { mapperDefaultInfo.mapFromEntity(it) }
            Response.success(userList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getUser(userName: String): Response<User> {
        val response = githubRetrofitService.getUser(userName)
        return if(response.isSuccessful) {
            val userDetailInfoDto = response.body()!!
            Response.success(mapperDetailInfo.mapFromEntity(userDetailInfoDto))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getRepositories(userName: String): Response<List<Repository>> {
        val response = githubRetrofitService.getRepositories(userName)
        return if (response.isSuccessful) {
            val repositoryDtoList = response.body()!!
            val repositoryList = repositoryDtoList.map { mapperRepository.mapFromEntity(it) }
            Response.success(repositoryList)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getRepository(userName: String, repo: String): Response<Repository> {
        val response = githubRetrofitService.getRepository(userName, repo)
        return if (response.isSuccessful) {
            val repositoryDetailDto = response.body()!!
            Response.success(mapperRepositoryDetailMapper.mapFromEntity(repositoryDetailDto))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}