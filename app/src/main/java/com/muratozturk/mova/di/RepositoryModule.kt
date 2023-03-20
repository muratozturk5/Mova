package com.muratozturk.mova.di

import com.muratozturk.mova.data.repository.MovaRepositoryImpl
import com.muratozturk.mova.domain.repository.MovaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(metflixRepositoryImpl: MovaRepositoryImpl): MovaRepository
}