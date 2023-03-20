package com.muratozturk.mova.di

import com.muratozturk.mova.data.source.local.LocalDataSourceImpl
import com.muratozturk.mova.data.source.preference.PreferenceDataSourceImpl
import com.muratozturk.mova.data.source.remote.RemoteDataSourceImpl
import com.muratozturk.mova.domain.source.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): DataSource.Remote

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): DataSource.Local

    @Binds
    abstract fun providePreferenceDataSource(preferenceDataSourceImpl: PreferenceDataSourceImpl): DataSource.Preference
}