package com.example.githubusersearch.di

import com.example.githubusersearch.common.constant.GITHUB_ACCESS_TOKEN
import com.example.githubusersearch.common.constant.GITHUB_API_BASIC_URL
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.UserDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideBaseUrl() = GITHUB_API_BASIC_URL

    @Singleton
    @Provides
    fun provideUserDtoMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

    @Singleton
    @Provides
    fun provideGithubRetrofitService():  GithubRetrofitService {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GithubRetrofitService::class.java)
    }

    @Singleton
    @Provides
    @Named("accept")
    fun provideAcceptToken(): String {
        return GITHUB_ACCESS_TOKEN
    }
}