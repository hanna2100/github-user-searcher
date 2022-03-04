package com.example.githubusersearch.di

import android.content.Context
import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.data.network.implementation.GithubDataSourceImpl
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.UserDtoMapper
import com.example.githubusersearch.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideGithubDataSource(
        githubRetrofitService: GithubRetrofitService,
        mapper: UserDtoMapper
    ): GithubDataSource {
        return GithubDataSourceImpl(githubRetrofitService, mapper)
    }

    @Singleton
    @Provides
    fun provideSearchUserInteractors(
        githubDataSource: GithubDataSource
    ): SearchUserInteractors {
        return SearchUserInteractors(githubDataSource)
    }

}