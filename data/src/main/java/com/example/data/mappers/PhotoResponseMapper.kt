package com.example.data.mappers


import com.example.data.models.PhotoItem
import com.example.data.models.User
import com.example.domain.data.api.PhotoItemApiResponse
import java.text.SimpleDateFormat
import java.util.*

class PhotoResponseMapper {
    companion object {
        fun toModel(results: List<PhotoItemApiResponse>): List<PhotoItem> {
            val list = mutableListOf<PhotoItem>()
            for(result in results) {
                val item = PhotoItem(result.id, user = User(result.user.id))
                item.width = result.width ?: 0
                item.height = result.height ?: 0

                if(!result.created_at.isNullOrEmpty()) {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        .parse(result.created_at)

                    if(date != null)
                        item.created = date
                }

                item.color = result.color.orEmpty()
                item.description = result.description.orEmpty()
                item.thumb = result.urls?.thumb.orEmpty()
                item.regular = result.urls?.regular.orEmpty()
                item.full = result.urls?.full.orEmpty()

                item.user.bio = result.user.bio
                item.user.instagram_username = result.user.instagram_username
                item.user.name = result.user.name
                item.user.portfolio_url = result.user.portfolio_url
                item.user.twitter_username = result.user.twitter_username
                item.user.username = result.user.username
                item.user.photoUrl = result.user.profile_image?.large

                item.userIsLoaded = true

                list.add(item)
            }
            return list
        }
    }
}