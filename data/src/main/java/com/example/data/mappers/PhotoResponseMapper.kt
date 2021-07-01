package com.example.data.mappers

import com.example.domain.data.PhotoItem
import com.example.domain.data.api.PhotoItemApiResponse
import java.util.*

class PhotoResponseMapper {
    companion object {
        fun toModel(results: List<PhotoItemApiResponse>?): List<PhotoItem>? {
            if(results == null)
                return null

            val list = mutableListOf<PhotoItem>()
            for(result in results)
                list.add(PhotoItem(
                    result.id,
                    result.width,
                    result.height,
                    Date(result.created_at),
                    result.color,
                    result.description,
                    result.urls.thumb,
                    result.urls.regular,
                    result.urls.full
                ))
            return list
        }
    }
}