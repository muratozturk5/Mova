package com.muratozturk.metflix.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muratozturk.metflix.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var progressBarJob: Job? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var action =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment()
        Firebase.auth.currentUser?.let {
            action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
        }

        progressBarJob = coroutineScope.launch {
            delay(3000)
            findNavController().navigate(action)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        progressBarJob?.cancel()
    }
}