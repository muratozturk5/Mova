package com.muratozturk.metflix.di

import com.muratozturk.metflix.data.repository.RemoteRepositoryImpl
import com.muratozturk.metflix.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(remoteRepositoryImpl: RemoteRepositoryImpl): MovieRepository
}