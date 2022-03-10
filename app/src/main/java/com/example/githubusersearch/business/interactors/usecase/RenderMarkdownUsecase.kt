package com.example.githubusersearch.business.interactors.usecase

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.framework.datasource.network.mappers.RenderedMarkdownHTMLMapper
import com.example.githubusersearch.framework.datasource.network.request.MarkDownText
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import retrofit2.Response

class RenderMarkdownUsecase(
    private val githubRetrofitService: GithubRetrofitService,
    private val renderedMarkdownHTMLMapper: RenderedMarkdownHTMLMapper
) {
    suspend fun execute(content: String): Response<Repository.MarkdownHTML> {
        val response = githubRetrofitService.renderMarkDown(text = MarkDownText(content))
        return if (response.isSuccessful) {
            val markdownHTML = renderedMarkdownHTMLMapper.mapFromEntity(response.body()?.string()?:"")
            Response.success(markdownHTML)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}