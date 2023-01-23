package com.muratozturk.metflix.ui.authentication.signinwithsocialmedia

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.Constants.REQ_SIGN_IN_GOOGLE
import com.muratozturk.metflix.common.LoadingScreen
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.showToast
import com.muratozturk.metflix.data.model.DialogArguments
import com.muratozturk.metflix.databinding.FragmentSignInWithSocialBinding
import com.muratozturk.metflix.ui.dialog.DialogFragmentDirections
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class SignInWithSocialFragment : Fragment(R.layout.fragment_sign_in_with_social) {
    private val binding by viewBinding(FragmentSignInWithSocialBinding::bind)
    private val viewModel: SignInWithSocialViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectData()
        initUI()

    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {
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
                                SignInWithSocialFragmentDirections.actionSignInWithSocialFragmentToDialogFragment(
                                    DialogArguments(
                                        getString(R.string.congratulations),
                                        getString(R.string.successful_sign_in),
                                        R.drawable.dialog_profile,
                                        DialogFragmentDirections.actionDialogFragmentToHomeFragment()
                                    )
                                )
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
                REQ_SIGN_IN_GOOGLE -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(resultContracts.data)
                    val account = task.getResult(ApiException::class.java)
                    val credential = getCredential(account?.idToken, null)
                    viewModel.signInWithCredential(credential)
                }
            }

        } catch (e: Exception) {
            Log.e("TAG-Activity", "onActivityResult: ${e.message}")
        }
    }

}