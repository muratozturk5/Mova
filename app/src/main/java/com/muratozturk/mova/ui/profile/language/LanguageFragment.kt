package com.muratozturk.mova.ui.profile.language

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle
import java.util.*

@AndroidEntryPoint
class LanguageFragment : Fragment(R.layout.fragment_language) {
    private val binding by viewBinding(FragmentLanguageBinding::bind)
    private val viewModel: LanguageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(binding) {
            with(viewModel) {

            }
        }
    }

    fun onClick(language: String, code: String) {
        viewModel.setLanguage(language)
        viewModel.setLanguageCode(code)

        val locale = Locale(code)
        val config = Configuration()
        config.setLocale(locale)
        val resources = context?.resources
        resources?.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()

        findNavController().popBackStack()
    }

    fun collectData() {
        with(viewModel) {
            with(binding) {

                lifecycleScope.launchWhenCreated {
                    currentLanguageCode.collectLatest { response ->

                        when (response) {
                            is Resource.Loading -> {
                            }
                            is Resource.Error -> {
                                requireActivity().showToast(
                                    getString(com.muratozturk.mova.R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                languagesRecycler.visible()

                                collectLanguages(response.data)

                            }
                        }
                    }
                }


            }
        }
    }

    private fun collectLanguages(currentLanguageCode: String) {
        with(viewModel) {
            with(binding) {

                lifecycleScope.launchWhenCreated {
                    languages.collectLatest { response ->

                        when (response) {
                            is Resource.Loading -> {
                                languagesRecycler.gone()
                            }
                            is Resource.Error -> {
                                languagesRecycler.gone()

                                requireActivity().showToast(
                                    getString(com.muratozturk.mova.R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                languagesRecycler.visible()

                                val adapter =
                                    LanguagesAdapter(response.data, currentLanguageCode)
                                languagesRecycler.adapter = adapter
                                adapter.onClick = ::onClick

                            }
                        }
                    }
                }


            }
        }
    }

}