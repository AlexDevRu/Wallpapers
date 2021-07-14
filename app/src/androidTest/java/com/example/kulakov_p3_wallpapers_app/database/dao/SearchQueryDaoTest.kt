package com.example.kulakov_p3_wallpapers_app.database.dao

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.PhotoDatabase
import com.example.data.database.dao.SearchQueryDao
import com.example.data.database.entities.SearchQueryEntity
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchQueryDaoTest {
    private lateinit var database: PhotoDatabase
    private lateinit var dao: SearchQueryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.searchQueryDao()


    }

    @After
    fun teardown() {
        database.close()
    }
}