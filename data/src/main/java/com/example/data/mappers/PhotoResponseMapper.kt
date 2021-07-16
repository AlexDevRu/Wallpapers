package com.example.data.mappers

import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import com.example.domain.mappers.IMapper
import com.example.data.api.responses.PhotoItemApiResponse
import java.text.SimpleDateFormat
import java.util.*

object PhotoResponseMapper: IMapper<PhotoItemApiResponse, PhotoItem> {
    override fun toModel(entity: PhotoItemApiResponse): PhotoItem {
        val item = PhotoItem(entity.id, user = User(entity.user.id))
        item.width = entity.width ?: 0
        item.height = entity.height ?: 0

        if(!entity.created_at.isNullOrEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(entity.created_at)

            if(date != null)
                item.created = date
        }

        item.color = entity.color
        item.description = entity.description
        item.thumb = entity.urls?.thumb
        item.regular = entity.urls?.regular
        item.full = entity.urls?.full

        item.user?.bio = entity.user.bio
        item.user?.instagram_username = entity.user.instagram_username
        item.user?.name = entity.user.name
        item.user?.portfolio_url = entity.user.portfolio_url
        item.user?.twitter_username = entity.user.twitter_username
        item.user?.username = entity.user.username
        item.user?.photoUrl = entity.user.profile_image?.large

        return item
    }

    override fun fromModel(model: PhotoItem): PhotoItemApiResponse {

        val user = model.user!!

        return PhotoItemApiResponse(
            model.id,
            model.color,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(model.created),
            model.height,
            PhotoItemApiResponse.Urls(model.full, model.regular, model.thumb),
            PhotoItemApiResponse.User(
                user.id,
                user.bio,
                user.instagram_username,
                user.name,
                user.portfolio_url,
                user.twitter_username,
                user.username,
                PhotoItemApiResponse.User.ProfileImage(user.photoUrl)
            ),
            model.width,
            model.description
        )
    }
}