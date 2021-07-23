package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.PhotoDatabase
import com.example.data.repositories.local.SearchQueryRepository
import com.example.domain.use_cases.queries.GetQueriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HistoryVMTest {
    private lateinit var viewModel: HistoryVM
    private lateinit var database: PhotoDatabase
    private lateinit var getQueriesUseCase: GetQueriesUseCase

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

        getQueriesUseCase = Mockito.spy(GetQueriesUseCase(searchRepository))

        viewModel = HistoryVM(getQueriesUseCase)
    }

    @After
    fun teardown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getQueriesTest() = runBlockingTest {
        viewModel.getQueries()
        Mockito.verify(getQueriesUseCase, Mockito.times(1)).invoke()
    }
}