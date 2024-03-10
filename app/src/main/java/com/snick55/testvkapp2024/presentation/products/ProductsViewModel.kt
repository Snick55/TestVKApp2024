package com.snick55.testvkapp2024.presentation.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snick55.testvkapp2024.domain.Product
import com.snick55.testvkapp2024.domain.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {


    private var job: Job? = null

    private val search = MutableLiveData("")

    private val innerProducts: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(PagingData.empty())

    val products: StateFlow<PagingData<Product>> = innerProducts.asStateFlow()




    init {
        job = loadProducts()
        search()
    }


    fun setSearch(search: String) {
        this.search.value = search
    }

    private fun search() = viewModelScope.launch {
        search.asFlow()
            .debounce(200)
            .flatMapLatest {
                repository.getSearchedPagingProduct(it)
            }
            .cachedIn(viewModelScope)
            .collect {
                innerProducts.value = it
            }
    }

    fun refresh() {
        job?.cancel()
        loadProducts()
    }

    private fun loadProducts() = viewModelScope.launch {
            repository.getPagedProducts()
                .cachedIn(viewModelScope)
                .collect {
                    innerProducts.value = it
                }
    }
}