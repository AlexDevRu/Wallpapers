package com.example.data.api

/*class UnsplashClient {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PhotoApiService::class.java)


    var metaInfo: SearchItem? = null
    var metaResult: Result<SearchItem?>? = null

    private suspend fun getPhotos(query: String?, position: Int, loadSize: Int): List<PhotoItem> {
        val photosResult = if(query.isNullOrEmpty()) {
            service.getPhotos(position, loadSize, PhotosPageSource.ACCESS_KEY)
        } else {
            val response = service.getPhotos(position, query, loadSize,
                PhotosPageSource.ACCESS_KEY
            )
            if(position == PhotosPageSource.STARTING_PAGE_INDEX) {
                //metaInfo = SearchItem(query = query, resultsCount = response.total)
            }
            response.results
        }

        return PhotoResponseMapper.toModel(photosResult)
    }
}*/