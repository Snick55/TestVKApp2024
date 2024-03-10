package com.snick55.testvkapp2024.presentation.products

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.snick55.testvkapp2024.R
import com.snick55.testvkapp2024.core.simpleScan
import com.snick55.testvkapp2024.core.viewBinding
import com.snick55.testvkapp2024.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_products) {

    private val binding by viewBinding<FragmentProductsBinding>()
    private val viewModel by viewModels<ProductsViewModel>()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductsList()
        setupSearch()
        setupSwipeToRefresh()

    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener {
            viewModel.setSearch(it.toString())
        }
    }


    private fun setupProductsList() {
        val adapter = ProductsAdapter(
            onItemClicked = {
                val action = ProductFragmentDirections.actionProductFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        )
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction = { adapter.retry() })
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.productsRecyclerView.adapter = adapterWithLoadState
        (binding.productsRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction = { adapter.retry() }
        )
        observeProducts(adapter)
        observeLoadState(adapter)
        handleListVisibility(adapter)
        handleScrollingToTopWhenSearching(adapter)
    }


    private fun handleListVisibility(adapter: ProductsAdapter) =
        viewLifecycleOwner.lifecycleScope.launch {
            getRefreshLoadStateFlow(adapter)
                .simpleScan(count = 3)
                .collectLatest { (beforePrevious, previous, current) ->
                    binding.productsRecyclerView.isInvisible = current is LoadState.Error
                            || previous is LoadState.Error
                            || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                            && current is LoadState.Loading)
                }
        }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun handleScrollingToTopWhenSearching(adapter: ProductsAdapter) =
        viewLifecycleOwner.lifecycleScope.launch {
            getRefreshLoadStateFlow(adapter)
                .simpleScan(count = 2)
                .collectLatest { (previousState, currentState) ->
                    if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                        binding.productsRecyclerView.scrollToPosition(0)
                    }
                }

        }

    private fun getRefreshLoadStateFlow(adapter: ProductsAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

    private fun observeProducts(adapter: ProductsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.products.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadState(adapter: ProductsAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

}