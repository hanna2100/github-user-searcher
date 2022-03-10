package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.UserDefaultInfoDto

class UserDefaultInfoMapper: EntityMapper<UserDefaultInfoDto, User> {
    override fun mapFromEntity(entity: UserDefaultInfoDto): User {
        return User(
            defaultInfo = User.DefaultInfo(
                login = entity.login,
                id = entity.id,
                avatarUrl = entity.avatarUrl
            )
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDefaultInfoDto {
        return UserDefaultInfoDto(
            id = domainModel.defaultInfo.id,
            avatarUrl = domainModel.defaultInfo.avatarUrl,
            login = domainModel.defaultInfo.login,
        )
    }
}