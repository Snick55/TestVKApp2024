package com.snick55.testvkapp2024.di

import com.snick55.testvkapp2024.data.ProductsRepositoryImpl
import com.snick55.testvkapp2024.domain.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindProductRepository(repository: ProductsRepositoryImpl): ProductsRepository

}