package com.example.githubusersearch.di

import android.content.Context
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.business.interactors.usecase.*
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
    fun provideSearchUserInteractors(
        searchUserUsecase: SearchUserUsecase
    ): SearchUserInteractors {
        return SearchUserInteractors(searchUserUsecase)
    }

    @Singleton
    @Provides
    fun provideUserDetailInteractors(
        getUserUsecase: GetUserUsecase,
        getRepositoriesUsecase: GetRepositoriesUsecase,
        getRepositoryUsecase: GetRepositoryUsecase,
        getContributorsUsecase: GetContributorsUsecase,
        getRedMeUsecase: GetRedMeUsecase,
        renderMarkdownUsecase: RenderMarkdownUsecase
    ): UserDetailInteractors {
        return UserDetailInteractors(
            getUserUsecase,
            getRepositoriesUsecase,
            getRepositoryUsecase,
            getContributorsUsecase,
            getRedMeUsecase,
            renderMarkdownUsecase
        )
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

    @Singleton
    @Provides
    fun provideGetUserUsecase(
        githubRetrofitService: GithubRetrofitService,
        userDetailInfoMapper: UserDetailInfoMapper
    ): GetUserUsecase {
        return GetUserUsecase(githubRetrofitService, userDetailInfoMapper)
    }

    @Singleton
    @Provides
    fun provideGetRepositoriesUsecase(
        githubRetrofitService: GithubRetrofitService,
        repositoryMapper: RepositoryMapper
    ): GetRepositoriesUsecase {
        return GetRepositoriesUsecase(githubRetrofitService, repositoryMapper)
    }

    @Singleton
    @Provides
    fun provideGetRepositoryUsecase(
        githubRetrofitService: GithubRetrofitService,
        repositoryDetailMapper: RepositoryDetailMapper
    ): GetRepositoryUsecase {
        return GetRepositoryUsecase(githubRetrofitService, repositoryDetailMapper)
    }

    @Singleton
    @Provides
    fun provideGetContributorsUsecase(
        githubRetrofitService: GithubRetrofitService,
        contributorsMapper: ContributorsMapper
    ): GetContributorsUsecase {
        return GetContributorsUsecase(githubRetrofitService, contributorsMapper)
    }

    @Singleton
    @Provides
    fun provideGetRedMeUsecase(
        githubRetrofitService: GithubRetrofitService,
        readMeMapper: ReadMeMapper
    ): GetRedMeUsecase {
        return GetRedMeUsecase(githubRetrofitService, readMeMapper)
    }

    @Singleton
    @Provides
    fun provideRenderMarkdownUsecase(
        githubRetrofitService: GithubRetrofitService,
        renderedMarkdownHTMLMapper: RenderedMarkdownHTMLMapper
    ): RenderMarkdownUsecase {
        return RenderMarkdownUsecase(githubRetrofitService, renderedMarkdownHTMLMapper)
    }
}