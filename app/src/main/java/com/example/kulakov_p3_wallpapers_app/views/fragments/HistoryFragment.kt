package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.data.aliases.DeleteQueryUseCase
import com.example.data.aliases.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.SearchHistoryAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentHistoryBinding
import com.example.kulakov_p3_wallpapers_app.view_models.HistoryVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding>
    (R.layout.fragment_history) {

    private val viewModel: HistoryVM by viewModels()

    private lateinit var adapter: SearchHistoryAdapter

    private var getJob: Job? = null

    @Inject
    lateinit var updateQueryUseCase: UpdateQueryUseCase

    @Inject
    lateinit var deleteQueryUseCase: DeleteQueryUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter = SearchHistoryAdapter(updateQueryUseCase, deleteQueryUseCase)

        binding.historyList.adapter = adapter

        getQueries()

        val callback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as SearchHistoryAdapter.SearchItemViewHolder).deleteItemFromDb()
            }
        }

        val myHelper = ItemTouchHelper(callback)
        myHelper.attachToRecyclerView(binding.historyList)
    }

    private fun getQueries() {
        getJob?.cancel()
        getJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getQueries().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}