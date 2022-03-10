package com.example.githubusersearch.di

import android.content.Context
import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource
import com.example.githubusersearch.business.data.network.implementation.GithubDataSourceImpl
import com.example.githubusersearch.business.interactors.SearchUserUsecase
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.business.interactors.userdetail.UserDetailInteractors
import com.example.githubusersearch.common.util.ResourcesProvider
import com.example.githubusersearch.common.util.ResourcesProviderImpl
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.*
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
        mapperDetailInfo: UserDetailInfoMapper,
        repositoryMapper: RepositoryMapper,
        repositoryDetailMapper: RepositoryDetailMapper,
        contributorsMapper: ContributorsMapper,
        readMeMapper: ReadMeMapper,
        renderedMarkdownHTMLMapper: RenderedMarkdownHTMLMapper
    ): GithubDataSource {
        return GithubDataSourceImpl(
            githubRetrofitService,
            mapperDetailInfo,
            repositoryMapper,
            repositoryDetailMapper,
            contributorsMapper,
            readMeMapper,
            renderedMarkdownHTMLMapper
        )
    }

    @Singleton
    @Provides
    fun provideSearchUserInteractors(
        searchUserUsecase: SearchUserUsecase
    ): SearchUserInteractors {
        return SearchUserInteractors(searchUserUsecase)
    }

    @Singleton
    @Provides
    fun provideUserDetailInteractors(
        githubDataSource: GithubDataSource
    ): UserDetailInteractors {
        return UserDetailInteractors(githubDataSource)
    }

    @Singleton
    @Provides
    fun provideResourcesProvider(@ApplicationContext app: Context): ResourcesProvider {
        return ResourcesProviderImpl(app)
    }

    @Singleton
    @Provides
    fun provideSearchUserUsecase(
        githubRetrofitService: GithubRetrofitService,
        userDefaultInfoMapper: UserDefaultInfoMapper
    ): SearchUserUsecase {
        return SearchUserUsecase(githubRetrofitService, userDefaultInfoMapper)
    }

}