package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.common.extensions.toDevLanguage
import com.example.githubusersearch.framework.datasource.network.model.RepositoryDto

class RepositoryMapper: EntityMapper<RepositoryDto, Repository> {
    override fun mapFromEntity(entity: RepositoryDto): Repository {
        return Repository(
            ownerAvatarUrl = entity.owner.avatarUrl,
            ownerLogin = entity.owner.login,
            name = entity.name?: "N/A",
            fullName = entity.fullName?: "N/A",
            language = entity.language.toDevLanguage(),
            forksCount = entity.forksCount,
            stargazersCount = entity.stargazersCount,
        )
    }

    override fun mapFromDomainModel(domainModel: Repository): RepositoryDto {
        return RepositoryDto (
            owner = RepositoryDto.RepositoryOwnerDto(
                login = domainModel.ownerLogin,
                avatarUrl = domainModel.ownerAvatarUrl
            ),
            name = if (domainModel.name == "N/A") null else domainModel.name,
            fullName = if (domainModel.name == "N/A") null else domainModel.fullName,
            language = if (domainModel.language.name == "N/A") null else domainModel.language.name,
            forksCount = domainModel.forksCount,
            stargazersCount = domainModel.stargazersCount,
        )
    }
}