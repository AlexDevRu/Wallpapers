package com.example.data.mappers

import com.example.data.database.entities.UserEntity
import com.example.data.models.User

class UserMapper {
    companion object {
        fun toModel(entity: UserEntity?): User? {
            if(entity  == null)
                return null
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

        fun fromModel(model: User): UserEntity {
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
}