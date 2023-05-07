package com.zerocoders.moviestack.di

import com.zerocoders.moviestack.network.RemoteDataSource
import com.zerocoders.moviestack.network.TmdbRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindTmdbRemoteDataSource(
        tmdbRemoteDataSourceImpl: TmdbRemoteDataSourceImpl
    ): RemoteDataSource
}