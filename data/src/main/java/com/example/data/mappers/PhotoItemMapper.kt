package com.example.data.mappers

import com.example.data.database.entities.PhotoItemEntity
import com.example.data.models.PhotoItem

class PhotoItemMapper {
    companion object {
        fun toModel(entity: PhotoItemEntity): PhotoItem {
            return PhotoItem(
                entity.id,
                entity.width,
                entity.height,
                entity.created,
                entity.color,
                entity.description,
                entity.thumb,
                entity.regular,
                entity.full
            )
        }

        fun toModel(entities: List<PhotoItemEntity>): List<PhotoItem> {
            val items = mutableListOf<PhotoItem>()
            for(entity in entities)
                items.add(toModel(entity))
            return items
        }

        fun fromModel(model: PhotoItem): PhotoItemEntity {
            return PhotoItemEntity(
                model.id,
                model.width,
                model.height,
                model.created,
                model.color,
                model.description,
                model.thumb,
                model.regular,
                model.full
            )
        }
    }
}