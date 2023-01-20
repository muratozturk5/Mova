package com.muratozturk.metflix.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override fun signUp(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        val createUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(Resource.Success(createUser))
    }.catch { emit(Resource.Error(it)) }

    override fun signIn(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        val loginUser =
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(Resource.Success(loginUser))

    }.catch { emit(Resource.Error(it)) }

    override fun signOut(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            firebaseAuth.signOut()
            emit(Resource.Success(true))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }.catch { emit(Resource.Error(it)) }

    override fun getUserInfo(): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        firebaseAuth.currentUser?.let { user ->
            emit(Resource.Success(user))
        }
    }.catch { emit(Resource.Error(it)) }
}