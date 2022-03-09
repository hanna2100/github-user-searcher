package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.util.EntityMapper

class RenderedMarkdownHTMLMapper: EntityMapper<String, Repository.MarkdownHTML> {
    override fun mapFromEntity(entity: String): Repository.MarkdownHTML {
        return Repository.MarkdownHTML(html = entity)
    }

    override fun mapFromDomainModel(domainModel: Repository.MarkdownHTML): String {
        return domainModel.html
    }
}