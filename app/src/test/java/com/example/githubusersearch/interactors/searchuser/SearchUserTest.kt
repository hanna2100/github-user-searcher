package com.example.githubusersearch.interactors.searchuser

import com.example.githubusersearch.business.interactors.SearchUserUsecase
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchUserTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    private lateinit var searchUserUsecase: SearchUserUsecase

    private lateinit var githubRetrofitService: GithubRetrofitService
    private val userDefaultInfoMapper = UserDefaultInfoMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("https://api.github.com/")

        githubRetrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(GithubRetrofitService::class.java)

        searchUserUsecase = SearchUserUsecase(
            githubRetrofitService = githubRetrofitService,
            userDefaultInfoMapper = userDefaultInfoMapper
        )
    }

}