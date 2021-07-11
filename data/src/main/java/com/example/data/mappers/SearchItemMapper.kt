package com.example.data.mappers

import com.example.data.database.entities.SearchQueryEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.SearchItem

object SearchItemMapper: IMapper<SearchQueryEntity, SearchItem> {
    override fun toModel(entity: SearchQueryEntity): SearchItem {
        return SearchItem(
            entity.id,
            entity.query,
            entity.resultsCount,
            entity.date,
            entity.isFavorite
        )
    }

    override fun fromModel(model: SearchItem): SearchQueryEntity {
        return SearchQueryEntity(
            model.id,
            model.query,
            model.resultsCount,
            model.date,
            model.isFavorite
        )
    }
}