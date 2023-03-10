package com.muratozturk.metflix.di

import android.content.Context
import androidx.room.Room
import com.muratozturk.metflix.data.source.local.MetflixDatabase
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
    fun provideRoomDb(@ApplicationContext appContext: Context): MetflixDatabase =
        Room.databaseBuilder(
            appContext,
            MetflixDatabase::class.java,
            "metflixDB.db"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(metflixDatabase: MetflixDatabase) = metflixDatabase.metflixDao()
}