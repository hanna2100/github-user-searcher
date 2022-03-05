package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.DefaultInfo
import com.example.githubusersearch.business.domain.model.DetailInfo
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.UserDetailInfoDto

class UserDetailInfoDtoMapper: EntityMapper<UserDetailInfoDto, User> {
    override fun mapFromEntity(entity: UserDetailInfoDto): User {
        return User(
            defaultInfo = DefaultInfo(
                login = entity.login,
                avatarUrl = entity.avatarUrl,
                id = entity.id
            ),
            detailInfo = DetailInfo(
                followers = entity.followers,
                following = entity.following,
                name = entity.name,
                location = entity.location,
                email = entity.email,
                reposUrl = entity.reposUrl
            )
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDetailInfoDto {
        return UserDetailInfoDto(
            login = domainModel.defaultInfo.login,
            id = domainModel.defaultInfo.id,
            avatarUrl = domainModel.defaultInfo.avatarUrl,
            followers = domainModel.detailInfo?.followers?: 0,
            following = domainModel.detailInfo?.following?: 0,
            name = domainModel.detailInfo?.name?: "",
            location = domainModel.detailInfo?.location?: "",
            email = domainModel.detailInfo?.email?: "",
            reposUrl = domainModel.detailInfo?.reposUrl?: ""
        )
    }
}