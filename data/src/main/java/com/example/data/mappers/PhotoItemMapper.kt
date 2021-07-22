package com.example.data.mappers

import com.example.data.database.entities.PhotoItemEntity
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import com.example.domain.mappers.IMapper

object PhotoItemMapper: IMapper<PhotoItemEntity, PhotoItem> {
    override fun toModel(entity: PhotoItemEntity): PhotoItem {
        return PhotoItem(
            entity.id,
            entity.width,
            entity.height,
            entity.created,
            entity.color,
            entity.description,
            entity.thumb,
            entity.regular,
            entity.full,
            entity.addedToFavorite,
            user = User(entity.userId)
        )
    }

    override fun fromModel(model: PhotoItem): PhotoItemEntity {
        return PhotoItemEntity(
            model.id,
            model.width,
            model.height,
            model.created,
            model.color,
            model.description,
            model.thumb,
            model.regular,
            model.full,
            model.addedToFavorite,
            model.user!!.id
        )
    }
}
