package com.muratozturk.metflix.ui.home.now_playing_series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.Resource
import com.muratozturk.metflix.common.showToast
import com.muratozturk.metflix.databinding.FragmentNowPlayingSeriesBinding
import com.muratozturk.metflix.domain.model.SerieUI
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class NowPlayingSeriesFragment : Fragment(R.layout.fragment_now_playing_series) {
    private val binding by viewBinding(FragmentNowPlayingSeriesBinding::bind)
    private val viewModel: NowPlayingSeriesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }


    private fun initUI() {
        with(binding) {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingSeries.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
                                com.muratozturk.metflix.common.LoadingScreen.displayLoading(
                                    requireContext(),
                                    false
                                )
                            }
                            is Resource.Error -> {
                                com.muratozturk.metflix.common.LoadingScreen.hideLoading()
                                requireActivity().showToast(
                                    getString(com.muratozturk.metflix.R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                com.muratozturk.metflix.common.LoadingScreen.hideLoading()

                                val adapter =
                                    NowPlayingSeriesAdapter(response.data as ArrayList<SerieUI>)
                                binding.recyclerViewNowPlayingSeries.adapter = adapter

                            }
                        }
                    }
                }

            }
        }
    }

}