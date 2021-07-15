package com.example.data.database.dao

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.data.database.PhotoDatabase
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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

            val addedPhoto = dao.getPhotoById(photoItem.id)

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

            val deletedPhoto = dao.getPhotoById(photoItem.id)

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

    @Test
    fun getCountPhotosByUserId() {
        runBlocking {
            val user = UserEntity(
                "3",
                "bio",
                "inst",
                "name",
                "portfolio",
                "twitter",
                "username",
                "photo url"
            )
            dao.insertUser(user)

            val photo1 = PhotoItemEntity(id = "1", userId = user.id)
            val photo2 = PhotoItemEntity(id = "2", userId = user.id)
            val photo3 = PhotoItemEntity(id = "3", userId = user.id)
            dao.createPhotoItem(photo1)
            dao.createPhotoItem(photo2)
            dao.createPhotoItem(photo3)

            val count = dao.getCountPhotosByUserId(user.id)

            assertFalse("count photos by user is wrong", count != 3)
        }
    }
}
