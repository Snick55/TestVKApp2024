package com.snick55.testvkapp2024.core

import com.snick55.testvkapp2024.data.entities.ProductCloud
import com.snick55.testvkapp2024.data.entities.SearchedProductCloud
import com.snick55.testvkapp2024.domain.Product

fun ProductCloud.toProduct(): Product = Product(
    id,
    title,
    description,
    price,
    discountPercentage,
    rating,
    stock,
    brand,
    category,
    thumbnail,
    images
)

fun SearchedProductCloud.toProduct():Product = Product(
    id,
    title,
    description,
    price,
    discountPercentage,
    rating,
    stock,
    brand,
    category,
    thumbnail,
    images
)