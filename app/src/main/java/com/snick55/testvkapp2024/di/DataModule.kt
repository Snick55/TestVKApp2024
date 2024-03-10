package com.snick55.testvkapp2024.di

import com.snick55.testvkapp2024.data.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindErrorHandler(errorHandler: ErrorHandler.BaseErrorHandler): ErrorHandler

}