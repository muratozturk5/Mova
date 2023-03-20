package com.muratozturk.mova.ui.authentication.signinwithpassword

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
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.data.model.DialogArguments
import com.muratozturk.mova.databinding.FragmentSignInWithPasswordBinding
import com.muratozturk.mova.ui.dialog.DialogFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class SignInWithPasswordFragment : Fragment(R.layout.fragment_sign_in_with_password) {
    private val binding by viewBinding(FragmentSignInWithPasswordBinding::bind)
    private val viewModel: SignInWithPasswordViewModel by viewModels()
    private var isPasswordShowing: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {
                backButton.setOnClickListener {
                    findNavController().popBackStack()
                }
                signUp.setOnClickListener {
                    val action =
                        SignInWithPasswordFragmentDirections.actionSignInWithPasswordFragmentToSignUpFragment()
                    findNavController().navigate(action)
                }
                showHidePassword.setOnClickListener {
                    if (isPasswordShowing) {
                        showHidePassword.setImageResource(R.drawable.dark_theme)
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
                signIn.setOnClickListener {
                    signIn(emailEditText.text.toString(), passwordEditText.text.toString())

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
        with(viewModel) {
            val laResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                    activityResult(activityResult)
                }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                user.collectLatest { response ->
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
                                SignInWithPasswordFragmentDirections.actionSignInWithPasswordFragmentToDialogFragment(
                                    DialogArguments(
                                        getString(R.string.congratulations),
                                        getString(R.string.successful_sign_in),
                                        R.drawable.dialog_profile,
                                        DialogFragmentDirections.actionDialogFragmentToHomeFragment()
                                    )
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                googleIntent.collectLatest { response ->
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
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                credentialSignInResult.collectLatest { response ->
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
                                SignInWithPasswordFragmentDirections.actionSignInWithPasswordFragmentToDialogFragment(
                                    DialogArguments(
                                        getString(R.string.congratulations),
                                        getString(R.string.successful_sign_in),
                                        R.drawable.dialog_profile,
                                        DialogFragmentDirections.actionDialogFragmentToHomeFragment()
                                    )
                                )
                            findNavController().navigate(action)

                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                facebookSignIn.collectLatest { response ->
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
                    }
                }
            }

        }
    }

    private fun activityResult(resultContracts: ActivityResult) {
        try {
            when (resultContracts.resultCode) {
                Constants.Authentication.REQ_SIGN_IN_GOOGLE -> {
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