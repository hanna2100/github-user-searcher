package com.example.githubusersearch.business.interactors.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubusersearch.business.domain.model.ReadMe
import com.example.githubusersearch.framework.datasource.network.mappers.ReadMeMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class GetRedMeUsecase(
    private val githubRetrofitService: GithubRetrofitService,
    private val readMeMapper: ReadMeMapper
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun execute(owner: String, repo: String): Response<ReadMe> {
        val response = githubRetrofitService.getReadMe(owner, repo)
        return if (response.isSuccessful) {
            val readMeDto = response.body()!!
            val readMe = readMeMapper.mapFromEntity(readMeDto)
            Response.success(readMe)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}