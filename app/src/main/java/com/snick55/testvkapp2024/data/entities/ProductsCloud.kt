package com.snick55.testvkapp2024.data.entities

import com.google.gson.annotations.SerializedName

data class ProductsCloud(
    @SerializedName("products")
    val products : List<ProductCloud>
)
