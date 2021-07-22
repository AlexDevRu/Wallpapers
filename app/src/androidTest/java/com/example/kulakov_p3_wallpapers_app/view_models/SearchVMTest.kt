package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.data.repositories.remote.PhotoApiRepository
import com.example.data.database.PhotoDatabase
import com.example.data.repositories.local.SearchQueryRepository
import com.example.data.repositories.test.FakeService
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.GetPhotosUseCase
import com.example.domain.use_cases.queries.InsertQueryUseCase
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchVMTest {
    private lateinit var viewModel: SearchVM
    private lateinit var database: PhotoDatabase
    private val service = FakeService()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoDatabase::class.java
        ).allowMainThreadQueries().build()

        val dao = database.searchQueryDao()
        val searchRepository = SearchQueryRepository(dao)

        val insertQueryUseCase = InsertQueryUseCase(searchRepository)

        val apiRepository = PhotoApiRepository(service, insertQueryUseCase)

        viewModel = SearchVM(GetPhotosUseCase(apiRepository))
    }

    @After
    fun teardown() {
        database.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun searchPhotosTest() = runBlockingTest {
        val adapter = PhotoAdapter()

        val dataSet = mutableListOf<Pair<String, PhotoItem>>()

        val countPages = (1..10).random()

        val keywords = mutableListOf("a", "b", "c")

        for(i in 1..(countPages * NETWORK_PAGE_SIZE)) {
            val item = Pair(keywords.random(), PhotoItem(i.toString()))
            dataSet.add(item)
        }

        service.dataSet = dataSet

        keywords.add("")
        val activeQuery = keywords.random()
        viewModel.searchQuery.onNext(activeQuery)

        val job = launch {
            viewModel.searchPhotos().collectLatest {
                adapter.submitData(it)
            }
        }

        advanceUntilIdle()

        Log.e("asd", "dataset - ${service.dataSet}")

        try {
            adapter.snapshot().items.forEach {
                assertFalse("item $it not match to search query $activeQuery",
                    activeQuery.isNotEmpty() &&
                            service.dataSet.none { v -> v.first == activeQuery && v.second == it }
                )
            }
        } finally {
            job.cancel()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun retryTest() = runBlockingTest {
        val oldResult = viewModel.searchPhotos()
        viewModel.retrySearch()
        assertFalse("data did not reloaded", oldResult == viewModel.searchPhotos())
    }

    @Test
    fun changeColumnCount() {
        val oldColumnCount = viewModel.columnListCount.get()
        viewModel.changeColumnCount()
        assertFalse("column count did not changed", viewModel.columnListCount.get() == oldColumnCount)
    }
}