package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.ContributorsDto

class ContributorsMapper : EntityMapper<ContributorsDto, Repository.Contributor> {
    override fun mapFromEntity(entity: ContributorsDto): Repository.Contributor {
        return Repository.Contributor(
            login = entity.login,
            avatarUrl = entity.avatarUrl,
            contributions = entity.contributions
        )
    }

    override fun mapFromDomainModel(domainModel: Repository.Contributor): ContributorsDto {
        return ContributorsDto(
            login = domainModel.login,
            avatarUrl = domainModel.avatarUrl,
            contributions = domainModel.contributions
        )
    }

}