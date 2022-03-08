package com.example.githubusersearch.di

import com.example.githubusersearch.common.constant.GITHUB_ACCESS_TOKEN
import com.example.githubusersearch.common.constant.GITHUB_API_BASIC_URL
import com.example.githubusersearch.common.util.AuthenticatedHeaderInterceptor
import com.example.githubusersearch.framework.datasource.network.abstraction.GithubRetrofitService
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryDetailMapper
import com.example.githubusersearch.framework.datasource.network.mappers.RepositoryMapper
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoDtoMapper
import com.example.githubusersearch.framework.datasource.network.mappers.UserDetailInfoDtoMapper
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
        return GsonConverterFactory.create()
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
    fun provideUserDefaultInfoDtoMapper(): UserDefaultInfoDtoMapper {
        return UserDefaultInfoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserDetailInfoDtoMapper(): UserDetailInfoDtoMapper {
        return UserDetailInfoDtoMapper()
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

}