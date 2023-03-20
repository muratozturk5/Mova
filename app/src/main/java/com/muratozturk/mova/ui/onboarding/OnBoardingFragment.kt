package com.muratozturk.mova.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding by viewBinding(FragmentOnBoardingBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding)
        {
            getStarted.setOnClickListener {
                val action =
                    OnBoardingFragmentDirections.actionOnBoardingFragmentToSignInWithSocialFragment()
                findNavController().navigate(action)
            }
        }
    }
}