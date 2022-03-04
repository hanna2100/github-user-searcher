package com.example.githubusersearch.business.util

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): Entity
}