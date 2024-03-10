package com.snick55.testvkapp2024.data

import com.snick55.testvkapp2024.data.entities.ProductsCloud
import com.snick55.testvkapp2024.data.entities.SearchedProductsCloud
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {


    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsCloud

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): SearchedProductsCloud

}