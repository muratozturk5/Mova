package com.muratozturk.metflix

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.databinding.FragmentSignUpBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private var isPasswordShowing: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            signIn.setOnClickListener {
                findNavController().popBackStack()
            }
            backButton.setOnClickListener { findNavController().popBackStack() }

            showHidePassword.setOnClickListener {
                if (isPasswordShowing) {
                    showHidePassword.setImageResource(R.drawable.show)
                    passwordEditText.transformationMethod = PasswordTransformationMethod()
                    isPasswordShowing = false
                } else {
                    showHidePassword.setImageResource(R.drawable.hide)
                    passwordEditText.transformationMethod = null
                    isPasswordShowing = true
                }
            }
            passwordEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                passwordEditText.changeFocusedInputTint(hasFocus)
                if (hasFocus) {
                    showHidePassword.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.text_color
                        )
                    )
                } else {
                    if (passwordEditText.text.toString().isEmpty()) {
                        showHidePassword.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.inactive_input
                            )
                        )
                    }
                }

            }
            emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                emailEditText.changeFocusedInputTint(hasFocus)
            }
        }
    }
}