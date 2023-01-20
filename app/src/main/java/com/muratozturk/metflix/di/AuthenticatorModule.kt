package com.muratozturk.metflix.di

import com.google.firebase.auth.FirebaseAuth
import com.muratozturk.metflix.data.repository.UserRepositoryImpl
import com.muratozturk.metflix.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AuthenticatorModule {
    @Provides
    @Singleton
    fun provideAuthenticator(
        firebaseAuth: FirebaseAuth
    ): UserRepository =
        UserRepositoryImpl(firebaseAuth)

}