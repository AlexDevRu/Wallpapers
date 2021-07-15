package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.api.PhotoApiRepository
import com.example.data.database.PhotoDatabase
import com.example.data.database.repositories.SearchQueryRepository
import com.example.domain.use_cases.photo.GetPhotosUseCase
import com.example.domain.use_cases.queries.InsertQueryUseCase
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchVMTest {
    private lateinit var viewModel: SearchVM
    private lateinit var database: PhotoDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoDatabase::class.java
        ).allowMainThreadQueries().build()

        val dao = database.searchQueryDao()
        val searchRepository = SearchQueryRepository(dao)
        val photoApiRepository = PhotoApiRepository(InsertQueryUseCase(searchRepository))
        viewModel = SearchVM(GetPhotosUseCase(photoApiRepository))
    }

    @After
    fun teardown() {
        database.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun searchPhotosTest() = runBlockingTest {
        val adapter = PhotoAdapter()

        val job = launch {
            viewModel.searchPhotos().collectLatest {
                adapter.submitData(it)
            }
        }

        advanceUntilIdle()

        adapter.snapshot().items

        // We need to cancel the launched job as coroutines.test framework checks for leaky jobs
        job.cancel()
    }
}