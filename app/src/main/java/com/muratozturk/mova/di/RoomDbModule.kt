package com.muratozturk.mova.di

import android.content.Context
import androidx.room.Room
import com.muratozturk.mova.data.source.local.MovaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {


    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context): MovaDatabase =
        Room.databaseBuilder(
            appContext,
            MovaDatabase::class.java,
            "metflixDB.db"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(metflixDatabase: MovaDatabase) = metflixDatabase.metflixDao()
}