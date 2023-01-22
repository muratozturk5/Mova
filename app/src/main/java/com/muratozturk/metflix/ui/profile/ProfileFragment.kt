package com.muratozturk.metflix.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.LoadingScreen
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.showToast
import com.muratozturk.metflix.databinding.FragmentProfileBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectData()
        initUI()
    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {
                signOut.setOnClickListener {
                    signOut()
                }
            }
        }
    }

    private fun collectData() {
        with(binding) {
            with(viewModel) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    authResult.collect { response ->
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
                                val action =
                                    ProfileFragmentDirections.actionProfileFragmentToSignInWithSocialFragment()
                                findNavController().navigate(action)

                            }
                            else -> {}
                        }
                    }

                }
            }
        }
    }
}