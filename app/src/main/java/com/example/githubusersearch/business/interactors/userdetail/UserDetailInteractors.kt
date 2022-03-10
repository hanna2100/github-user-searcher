package com.example.githubusersearch.business.interactors.userdetail

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubusersearch.business.domain.model.ReadMe
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.usecase.*
import retrofit2.Response

class UserDetailInteractors(
    private val getUserUsecase: GetUserUsecase,
    private val getRepositoriesUsecase: GetRepositoriesUsecase,
    private val getRepositoryUsecase: GetRepositoryUsecase,
    private val getContributorsUsecase: GetContributorsUsecase,
    private val getRedMeUsecase: GetRedMeUsecase,
    private val renderMarkdownUsecase: RenderMarkdownUsecase
) {

    suspend fun getUser(userName: String): Response<User> {
        return getUserUsecase.execute(userName)
    }

    suspend fun getRepositories(userName: String): Response<List<Repository>> {
        return getRepositoriesUsecase.execute(userName)
    }

    suspend fun getRepository(owner: String, repo: String): Response<Repository> {
        return getRepositoryUsecase.execute(owner, repo)
    }

    suspend fun getContributors(owner: String, repo: String): Response<List<Repository.Contributor>> {
        return getContributorsUsecase.execute(owner, repo)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getReadMe(owner: String, repo: String): Response<ReadMe> {
        return getRedMeUsecase.execute(owner, repo)
    }

    suspend fun renderMarkdown(content: String): Response<Repository.MarkdownHTML> {
        return renderMarkdownUsecase.execute(content)
    }

}