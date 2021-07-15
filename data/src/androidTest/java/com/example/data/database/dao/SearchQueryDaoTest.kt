package com.example.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.PhotoDatabase
import com.example.data.database.entities.SearchQueryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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

    @Test
    fun createSearchQuery() {
        runBlocking {
            val query = SearchQueryEntity(
                query = "query"
            )
            dao.create(query)

            val addedQuery = dao.getQueryById(query.id)

            Assert.assertFalse("query did not be created in the database", addedQuery == null)
        }
    }

    @Test
    fun updateSearchQuery() {
        runBlocking {
            val query = SearchQueryEntity(
                query = "query"
            )
            dao.create(query)
            val addedQuery = dao.getQueryById(query.id)
            addedQuery!!.query = "updated query"
            dao.update(addedQuery)

            Assert.assertFalse("query did not be updated", addedQuery.query == query.query)
        }
    }

    @Test
    fun deleteSearchQuery() {
        runBlocking {
            val query = SearchQueryEntity(
                query = "query"
            )
            dao.create(query)
            dao.delete(query)

            val deletedQuery = dao.getQueryById(query.id)

            Assert.assertFalse("query did not be updated", deletedQuery != null)
        }
    }
}