package com.muratozturk.metflix.ui.authentication.signup

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
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


                continueWithGoogle.setOnClickListener {
                    signInGoogle()
                }
                continueWithFacebook.setOnClickListener {

                    loginManager.logInWithReadPermissions(
                        requireActivity(),
                        mCallbackManager,
                        mutableListOf("email", "public_profile")
                    )
                }
                continueWithGithub.setOnClickListener {
                    signInGithub(requireActivity())
                }
            }
        }
    }

    private fun collectData() {
        val laResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                activityResult(activityResult)
            }
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
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

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

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                googleIntent.collect { response ->
                    when (response) {
                        is Resource.Loading -> {
                            LoadingScreen.displayLoading(requireContext(), false)
                        }
                        is Resource.Error -> {
                            LoadingScreen.hideLoading()
                            requireActivity().showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }
                        is Resource.Success -> {
                            LoadingScreen.hideLoading()
                            laResult.launch(response.data)

                        }
                        else -> {}
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                credentialSignInResult.collect { response ->
                    when (response) {
                        is Resource.Loading -> {
                            LoadingScreen.displayLoading(requireContext(), false)
                        }
                        is Resource.Error -> {
                            LoadingScreen.hideLoading()
                            requireActivity().showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }
                        is Resource.Success -> {
                            LoadingScreen.hideLoading()
                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                            findNavController().navigate(action)

                        }
                        else -> {}
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                facebookSignIn.collect { response ->
                    when (response) {
                        is Resource.Loading -> {
                            LoadingScreen.displayLoading(requireContext(), false)
                        }
                        is Resource.Error -> {
                            LoadingScreen.hideLoading()
                            requireActivity().showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }
                        is Resource.Success -> {
                            LoadingScreen.hideLoading()
                            signInWithCredential(response.data)

                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun activityResult(resultContracts: ActivityResult) {
        try {
            when (resultContracts.resultCode) {
                Constants.REQ_SIGN_IN_GOOGLE -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(resultContracts.data)
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    viewModel.signInWithCredential(credential)
                }
            }

        } catch (e: Exception) {
            Log.e("TAG-Activity", "onActivityResult: ${e.message}")
        }
    }
}