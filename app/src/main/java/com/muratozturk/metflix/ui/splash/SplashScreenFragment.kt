package com.muratozturk.metflix.ui.splash

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muratozturk.metflix.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        var action =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment()
        Firebase.auth.currentUser?.let {
            action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            findNavController().navigate(action)
        }
    }
}