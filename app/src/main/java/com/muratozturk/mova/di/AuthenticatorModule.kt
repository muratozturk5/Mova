package com.muratozturk.mova.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.muratozturk.mova.data.repository.AuthRepositoryImpl
import com.muratozturk.mova.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AuthenticatorModule {
    @Provides
    @Singleton
    fun provideAuthenticator(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context
    ): AuthRepository =
        AuthRepositoryImpl(firebaseAuth, context)
}