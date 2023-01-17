package com.muratozturk.metflix

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val action =
                SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment()
            findNavController().navigate(action)
        }
    }
}