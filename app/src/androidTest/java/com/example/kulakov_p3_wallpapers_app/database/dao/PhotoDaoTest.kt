package com.example.kulakov_p3_wallpapers_app.database.dao

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.data.database.PhotoDatabase
import com.example.data.database.dao.PhotoDao
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.UserEntity
import com.example.data.database.repositories.PhotoRepository
import com.example.data.mappers.PhotoItemMapper
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class PhotoDaoTest {
    private lateinit var database: PhotoDatabase
    private lateinit var dao: PhotoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.photoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private suspend fun getPhotoItemById(id: String): PhotoItemEntity? {
        val pagingSource = dao.getPhotos()
        val actual = pagingSource.load(PagingSource.LoadParams.Refresh(1, 50, false))
        return (actual as? PagingSource.LoadResult.Page)?.data?.find { it.id == id }
    }

    @Test
    fun createPhotoItem() {
        runBlocking {
            val photoItem = PhotoItemEntity(
                "1",
                100,
                100,
                color = "color",
                description = "description",
                thumb = "thumb",
                regular = "regular",
                full = "full",
                userId = "1"
            )

            dao.createPhotoItem(photoItem)

            val addedPhoto = getPhotoItemById(photoItem.id)

            assertFalse("photo did not be inserted in the database", addedPhoto == null)
        }
    }

    @Test
    fun deletePhotoItem() {
        runBlocking {
            val photoItem = PhotoItemEntity(
                "1",
                100,
                100,
                color = "color",
                description = "description",
                thumb = "thumb",
                regular = "regular",
                full = "full",
                userId = "1"
            )

            dao.createPhotoItem(photoItem)
            dao.deletePhotoItem(photoItem)

            val deletedPhoto = getPhotoItemById(photoItem.id)

            assertFalse("photo did not be deleted from the database", deletedPhoto != null)
        }
    }

    @Test
    fun insertUser() {
        runBlocking {
            val user = UserEntity(
                "1",
                "bio",
                "inst",
                "name",
                "portfolio",
                "twitter",
                "username",
                "photo url"
            )
            dao.insertUser(user)
            val insertedUser = dao.getUserById(user.id)
            Log.e("asd", "user ${insertedUser}")
            assertFalse("user did not be inserted in the database", insertedUser == null)
        }
    }

    @Test
    fun deleteUser() {
        runBlocking {
            val user = UserEntity(
                "2",
                "bio",
                "inst",
                "name",
                "portfolio",
                "twitter",
                "username",
                "photo url"
            )

            dao.insertUser(user)
            dao.deleteUser(user)

            val deletedUser = dao.getUserById(user.id)

            assertFalse("user did not be deleted from the database", deletedUser != null)
        }
    }
}
