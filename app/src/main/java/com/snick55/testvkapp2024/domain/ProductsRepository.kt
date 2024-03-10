package com.snick55.testvkapp2024.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {


    fun getPagedProducts(): Flow<PagingData<Product>>

    fun getSearchedPagingProduct(value: String): Flow<PagingData<Product>>

}