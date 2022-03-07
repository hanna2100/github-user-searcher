package com.example.githubusersearch.di

import android.content.Context
import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.data.network.implementation.GithubDataSourceImpl
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.business.interactors.userdetail.UserDetailInteractors
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoDtoMapper
import com.example.githubusersearch.framework.datasource.network.mappers.UserDetailInfoDtoMapper
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
        mapperDefaultInfo: UserDefaultInfoDtoMapper,
        mapperDetailInfo: UserDetailInfoDtoMapper,
    ): GithubDataSource {
        return GithubDataSourceImpl(githubRetrofitService, mapperDefaultInfo, mapperDetailInfo)
    }

    @Singleton
    @Provides
    fun provideSearchUserInteractors(
        githubDataSource: GithubDataSource
    ): SearchUserInteractors {
        return SearchUserInteractors(githubDataSource)
    }

    @Singleton
    @Provides
    fun provideUserDetailInteractors(
        githubDataSource: GithubDataSource
    ): UserDetailInteractors {
        return UserDetailInteractors(githubDataSource)
    }


}