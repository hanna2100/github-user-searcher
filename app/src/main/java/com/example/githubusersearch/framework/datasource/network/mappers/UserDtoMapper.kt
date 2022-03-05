package com.example.githubusersearch.framework.datasource.network.mappers

import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.UserDto

class UserDtoMapper: EntityMapper<UserDto, User> {
    override fun mapFromEntity(entity: UserDto): User {
        return User(
            id = entity.id,
            avatarUrl = entity.avatarUrl,
            login = entity.login
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            id = domainModel.id,
            avatarUrl = domainModel.avatarUrl,
            login = domainModel.login,
        )
    }
}