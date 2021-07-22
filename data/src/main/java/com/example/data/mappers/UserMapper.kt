package com.example.data.mappers

import com.example.data.database.entities.UserEntity
import com.example.domain.models.User
import com.example.domain.mappers.IMapper

object UserMapper: IMapper<UserEntity, User> {
    override fun toModel(entity: UserEntity): User {
        return User(
            entity.id,
            entity.bio,
            entity.instagram_username,
            entity.name,
            entity.portfolio_url,
            entity.twitter_username,
            entity.username,
            entity.photoUrl
        )
    }

    override fun fromModel(model: User): UserEntity {
        return UserEntity(
            model.id,
            model.bio,
            model.instagram_username,
            model.name,
            model.portfolio_url,
            model.twitter_username,
            model.username,
            model.photoUrl
        )
    }
}