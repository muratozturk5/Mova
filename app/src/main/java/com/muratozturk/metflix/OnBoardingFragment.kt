package com.muratozturk.metflix

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.databinding.FragmentOnBoardingBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding by viewBinding(FragmentOnBoardingBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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