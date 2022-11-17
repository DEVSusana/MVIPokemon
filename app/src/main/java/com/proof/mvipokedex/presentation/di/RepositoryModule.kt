package com.proof.mvipokedex.presentation.di

import com.proof.mvipokedex.data.repository.RepositoryImpl
import com.proof.mvipokedex.data.repository.dataSource.RemoteDataSource
import com.proof.mvipokedex.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource)
    }
}