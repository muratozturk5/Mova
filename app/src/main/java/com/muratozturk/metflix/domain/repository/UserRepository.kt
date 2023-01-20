package com.muratozturk.metflix.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.muratozturk.metflix.common.Resource
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun signUp(email: String, password: String): Flow<Resource<AuthResult>>
    fun signIn(email: String, password: String): Flow<Resource<AuthResult>>
    fun signOut(): Flow<Resource<Boolean>>
    fun getUserInfo(): Flow<Resource<FirebaseUser>>
}