package com.muratozturk.metflix.ui.login.signup

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.LoadingScreen
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.changeFocusedInputTint
import com.muratozturk.metflix.common.showToast
import com.muratozturk.metflix.databinding.FragmentSignUpBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()
    private var isPasswordShowing: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {
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
                signUp.setOnClickListener {
                    viewModel.signUp(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                }
            }
        }
    }

    private fun collectData() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                user.collect { response ->
                    when (response) {
                        is Resource.Loading -> {
                            LoadingScreen.displayLoading(
                                requireContext(),
                                false
                            )
                        }
                        is Resource.Error -> {
                            LoadingScreen.hideLoading()
                            requireActivity().showToast(
                                getString(R.string.error),
                                getString(R.string.email_or_password_is_not_valid),
                                MotionToastStyle.ERROR
                            )
                            Log.e("Response", response.throwable.localizedMessage ?: "Error")

                        }
                        is Resource.Success -> {
                            LoadingScreen.hideLoading()
                            requireActivity().showToast(
                                title = null,
                                getString(R.string.sign_up_success),
                                MotionToastStyle.SUCCESS
                            )
                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }
                        else -> {}
                    }
                }

            }


        }
    }
}