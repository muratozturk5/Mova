package com.muratozturk.metflix.di

import com.muratozturk.metflix.data.data_source.RemoteDataSourceImpl
import com.muratozturk.metflix.domain.source.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSourceImpl: RemoteDataSourceImpl): DataSource.Remote
}