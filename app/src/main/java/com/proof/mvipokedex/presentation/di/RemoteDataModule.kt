package com.proof.mvipokedex.presentation.di

import com.proof.mvipokedex.data.api.ApiService
import com.proof.mvipokedex.data.repository.dataSource.RemoteDataSource
import com.proof.mvipokedex.data.repository.dataSourceImpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
}