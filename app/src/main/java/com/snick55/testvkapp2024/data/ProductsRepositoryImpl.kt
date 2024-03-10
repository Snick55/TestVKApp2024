package com.snick55.testvkapp2024.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.snick55.testvkapp2024.core.toProduct
import com.snick55.testvkapp2024.data.entities.ProductCloud
import com.snick55.testvkapp2024.data.entities.SearchedProductCloud
import com.snick55.testvkapp2024.di.IoDispatcher
import com.snick55.testvkapp2024.domain.Product
import com.snick55.testvkapp2024.domain.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: ErrorHandler
) : ProductsRepository {

    override fun getPagedProducts(): Flow<PagingData<Product>> {
        val loader = object : Loader<ProductCloud> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<ProductCloud> {
                return getProducts(pageIndex, pageSize)
            }
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { ProductsPagingSource(loader,errorHandler) }
        ).flow
            .map { pagingCloud ->
                pagingCloud.map { productCloud ->
                    productCloud.toProduct()
                }
            }
    }


    override fun getSearchedPagingProduct(value: String): Flow<PagingData<Product>> {
        val loader = object : Loader<SearchedProductCloud> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<SearchedProductCloud> {
                return getSearchedProducts(pageIndex, pageSize, value)
            }
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { ProductsPagingSource(loader,errorHandler) }
        ).flow
            .map {pagingSearchedCloud->
                pagingSearchedCloud.map { productSearchedCloud ->
                    productSearchedCloud.toProduct()
                }
            }
    }

    private suspend fun getSearchedProducts(
        pageIndex: Int,
        pageSize: Int,
        query: String
    ): List<SearchedProductCloud> = withContext(ioDispatcher) {
        val offset = pageIndex * pageSize
        val products = productsApi.searchProducts(query,offset, pageSize)
        return@withContext products.products
    }

    private suspend fun getProducts(pageIndex: Int, pageSize: Int): List<ProductCloud> =
        withContext(ioDispatcher) {
            val offset = pageIndex * pageSize
            val products = productsApi.getProducts(offset, pageSize)
            return@withContext products.products
        }


    private companion object {
        private const val PAGE_SIZE = 20
    }

}