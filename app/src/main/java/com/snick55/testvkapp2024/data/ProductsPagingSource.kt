package com.snick55.testvkapp2024.data

import androidx.paging.PagingSource
import androidx.paging.PagingState


class ProductsPagingSource<T: Any>(
    private val loader: Loader<T>,
    private val errorHandler: ErrorHandler
): PagingSource<Int,T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageIndex = params.key ?: 0

        return try {
            val products = loader.load(pageIndex, params.loadSize)
          return LoadResult.Page(
              data = products,
              prevKey = if (pageIndex == 0) null else pageIndex - 1,
              nextKey = if (products.size == params.loadSize) pageIndex + 1 else
                  null


          )
        }catch (e: Exception){
            LoadResult.Error(throwable = errorHandler.handle(e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }


}