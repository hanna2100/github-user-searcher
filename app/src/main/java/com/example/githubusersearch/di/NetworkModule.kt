package com.example.githubusersearch.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubusersearch.common.constant.GITHUB_ACCESS_TOKEN
import com.example.githubusersearch.common.constant.GITHUB_API_BASIC_URL
import com.example.githubusersearch.common.util.AuthenticatedHeaderInterceptor
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideAuthenticatedHeaderInterceptor(): AuthenticatedHeaderInterceptor {
        return AuthenticatedHeaderInterceptor(GITHUB_ACCESS_TOKEN)
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        provideAuthenticatedHeaderInterceptor: AuthenticatedHeaderInterceptor
    ): OkHttpClient {
        val client = OkHttpClient().newBuilder()
        client.callTimeout(40, TimeUnit.SECONDS)
        client.connectTimeout(40, TimeUnit.SECONDS)
        client.readTimeout(40, TimeUnit.SECONDS)
        client.writeTimeout(40, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(provideAuthenticatedHeaderInterceptor)
        client.build()
        return client.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GITHUB_API_BASIC_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubRetrofitService(retrofit: Retrofit):  GithubRetrofitService {
        return retrofit.create(GithubRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDefaultInfoDtoMapper(): UserDefaultInfoMapper {
        return UserDefaultInfoMapper()
    }

    @Singleton
    @Provides
    fun provideUserDetailInfoDtoMapper(): UserDetailInfoMapper {
        return UserDetailInfoMapper()
    }

    @Singleton
    @Provides
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }

    @Singleton
    @Provides
    fun provideRepositoryDetailMapper(): RepositoryDetailMapper {
        return RepositoryDetailMapper()
    }

    @Singleton
    @Provides
    fun provideContributorsDtoMapper(): ContributorsMapper {
        return ContributorsMapper()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideReadMeDtoMapper(): ReadMeMapper {
        return ReadMeMapper()
    }

    @Singleton
    @Provides
    fun provideRenderedMarkDownHTMLMapper() :RenderedMarkdownHTMLMapper {
        return RenderedMarkdownHTMLMapper()
    }
}