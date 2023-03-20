package com.muratozturk.mova.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.muratozturk.mova.common.Constants.Authentication.WEB_CLIENT_ID
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context
) : AuthRepository {

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

    override fun signInWithGoogle(): Flow<Resource<Intent>> = flow {
        emit(Resource.Loading)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID).requestProfile()
            .requestEmail().build()

        val signInClient = GoogleSignIn.getClient(context, gso).signInIntent

        emit(Resource.Success(signInClient))

    }.catch { emit(Resource.Error(it)) }


    override fun signInWithGithub(activity: Activity): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)

        val githubAuthProvider = OAuthProvider.newBuilder("github.com")
            .build()

        emit(
            Resource.Success(
                firebaseAuth.startActivityForSignInWithProvider(
                    activity,
                    githubAuthProvider
                ).await()
            )
        )

    }.catch { emit(Resource.Error(it)) }

    override fun signInWithCredential(credential: AuthCredential): Flow<Resource<AuthResult>> =
        flow {
            emit(Resource.Loading)
            emit(Resource.Success(firebaseAuth.signInWithCredential(credential).await()))
        }.catch { emit(Resource.Error(it)) }

}