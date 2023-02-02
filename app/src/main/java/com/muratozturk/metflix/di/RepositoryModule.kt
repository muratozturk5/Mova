package com.muratozturk.metflix.di

import com.muratozturk.metflix.data.repository.MetflixRepositoryImpl
import com.muratozturk.metflix.domain.repository.MetflixRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(remoteRepositoryImpl: MetflixRepositoryImpl): MetflixRepository
}