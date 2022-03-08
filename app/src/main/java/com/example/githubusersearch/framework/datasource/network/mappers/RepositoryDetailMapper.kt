package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.common.extensions.toDevLanguage
import com.example.githubusersearch.framework.datasource.network.model.RepositoryDetailDto
import com.example.githubusersearch.framework.datasource.network.model.RepositoryOwnerDto

class RepositoryDetailMapper : EntityMapper<RepositoryDetailDto, Repository> {
    override fun mapFromEntity(entity: RepositoryDetailDto): Repository {
        return Repository(
            ownerAvatarUrl = entity.owner.avatarUrl,
            ownerLogin = entity.owner.login,
            name = entity.name?: "N/A",
            fullName = entity.fullName?: "N/A",
            language = entity.language.toDevLanguage(),
            forksCount = entity.forksCount,
            stargazersCount = entity.stargazersCount,
            detailInfo = Repository.DetailInfo(
                description = entity.description?: "N/A",
                watchersCount = entity.watchersCount,
                openIssuesCount = entity.openIssuesCount,
                pushedAt = entity.pushedAt,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
            )
        )
    }

    override fun mapFromDomainModel(domainModel: Repository): RepositoryDetailDto {
        return RepositoryDetailDto (
            owner = RepositoryOwnerDto(
                login = domainModel.ownerLogin,
                avatarUrl = domainModel.ownerAvatarUrl
            ),
            name = if (domainModel.name == "N/A") null else domainModel.name,
            fullName = if (domainModel.name == "N/A") null else domainModel.fullName,
            language = if (domainModel.language.name == "N/A") null else domainModel.language.name,
            forksCount = domainModel.forksCount,
            stargazersCount = domainModel.stargazersCount,
            description = domainModel.detailInfo?.description,
            watchersCount = domainModel.detailInfo?.watchersCount?: 0,
            openIssuesCount = domainModel.detailInfo?.openIssuesCount?: 0,
            pushedAt = domainModel.detailInfo?.pushedAt?: "",
            createdAt = domainModel.detailInfo?.createdAt?: "",
            updatedAt = domainModel.detailInfo?.updatedAt?: ""
        )
    }

}