package com.muratozturk.mova.ui.profile.logout

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.databinding.FragmentLogoutDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class LogoutDialogFragment : BottomSheetDialogFragment(R.layout.fragment_logout_dialog) {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let {
            if (window != null) {
                window.statusBarColor = it.getColor(android.R.color.transparent)
            }
        }
        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.skipCollapsed = true

        }
    }

    private val binding by viewBinding(FragmentLogoutDialogBinding::bind)
    private val viewModel: LogoutDialogViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(binding) {
            with(viewModel) {


                cancelBtn.setOnClickListener {
                    findNavController().popBackStack()
                }
                deleteButton.setOnClickListener {
                    signOut()
                }
            }
        }

    }

    private fun collectData() {
        with(binding) {
            with(viewModel) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    authResult.collectLatest { response ->
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
                                    LogoutDialogFragmentDirections.actionLogoutDialogFragmentToSignInWithSocialFragment()
                                findNavController().navigate(action)

                            }
                        }
                    }

                }
            }
        }
    }
}