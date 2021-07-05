package com.example.data.mappers

import com.example.data.database.entities.SearchQueryEntity
import com.example.domain.data.SearchItem

class SearchItemMapper {
    companion object {
        fun toModel(entity: SearchQueryEntity): SearchItem {
            return SearchItem(
                entity.id,
                entity.query,
                entity.resultsCount,
                entity.date,
                entity.isFavorite
            )
        }

        fun toModel(entities: List<SearchQueryEntity>): List<SearchItem> {
            val items = mutableListOf<SearchItem>()
            for(entity in entities)
                items.add(toModel(entity))
            return items
        }

        fun fromModel(model: SearchItem): SearchQueryEntity {
            return SearchQueryEntity(
                model.id,
                model.query,
                model.resultsCount,
                model.date,
                model.isFavorite
            )
        }
    }
}