package com.muratozturk.metflix

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.databinding.FragmentSignInWithSocialBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SignInWithSocialFragment : Fragment(R.layout.fragment_sign_in_with_social) {
    private val binding by viewBinding(FragmentSignInWithSocialBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            signUp.setOnClickListener {
                val action =
                    SignInWithSocialFragmentDirections.actionSignInWithSocialFragmentToSignUpFragment()
                findNavController().navigate(action)

            }
            signInWithPassword.setOnClickListener {
                val action =
                    SignInWithSocialFragmentDirections.actionSignInWithSocialFragmentToSignInWithPasswordFragment()
                findNavController().navigate(action)
            }
            backButton.setOnClickListener { findNavController().popBackStack() }
        }
    }
}