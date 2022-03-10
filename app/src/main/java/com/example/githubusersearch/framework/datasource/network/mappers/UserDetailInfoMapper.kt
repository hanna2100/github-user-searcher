package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.UserDetailInfoDto

class UserDetailInfoMapper: EntityMapper<UserDetailInfoDto, User> {
    override fun mapFromEntity(entity: UserDetailInfoDto): User {
        return User(
            defaultInfo = User.DefaultInfo(
                login = entity.login,
                avatarUrl = entity.avatarUrl,
                id = entity.id
            ),
            detailInfo = User.DetailInfo(
                followers = entity.followers,
                following = entity.following,
                name = entity.name ?: "N/A",
                location = entity.location ?: "N/A",
                email = entity.email ?: "N/A",
                bio = entity.bio ?: "N/A",
                blog = entity.blog ?: "N/A",
                createAt = entity.createAt
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
            bio = domainModel.detailInfo?.bio?: "",
            blog = domainModel.detailInfo?.blog?: "",
            createAt = domainModel.detailInfo?.createAt?: ""
        )
    }
}