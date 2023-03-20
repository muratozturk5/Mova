package com.muratozturk.mova.domain.repository

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.muratozturk.mova.common.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun signUp(email: String, password: String): Flow<Resource<AuthResult>>
    fun signIn(email: String, password: String): Flow<Resource<AuthResult>>
    fun signOut(): Flow<Resource<Boolean>>
    fun getUserInfo(): Flow<Resource<FirebaseUser>>
    fun signInWithGoogle(): Flow<Resource<Intent>>
    fun signInWithGithub(activity: Activity): Flow<Resource<AuthResult>>
    fun signInWithCredential(credential: AuthCredential): Flow<Resource<AuthResult>>
}