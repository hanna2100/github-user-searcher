package com.example.githubusersearch.business.util

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): Entity
}

interface EntityMapper2<Entity, DomainModel> {
    suspend fun mapFromEntity(entity: Entity): DomainModel
    suspend fun mapFromDomainModel(domainModel: DomainModel): Entity
}