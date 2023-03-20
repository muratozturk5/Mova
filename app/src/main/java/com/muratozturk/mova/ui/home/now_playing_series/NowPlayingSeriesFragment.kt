package com.muratozturk.mova.ui.home.now_playing_series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.muratozturk.mova.R
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.common.gone
import com.muratozturk.mova.common.showToast
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.common.visible
import com.muratozturk.mova.databinding.FragmentNowPlayingSeriesBinding
import com.muratozturk.mova.ui.home.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NowPlayingSeriesFragment : Fragment(R.layout.fragment_now_playing_series) {
    private val binding by viewBinding(FragmentNowPlayingSeriesBinding::bind)
    private val viewModel: NowPlayingSeriesViewModel by viewModels()
    private val adapter: NowPlayingSeriesAdapter by lazy { NowPlayingSeriesAdapter(::onClickItem) }

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

    private fun onClickItem(id: Int) {
        val action =
            NowPlayingSeriesFragmentDirections.actionNowPlayingSeriesFragmentToDetailsFragment(
                id,
                MediaTypeEnum.SERIE
            )
        findNavController().navigate(action)
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingSeries.collectLatest { response ->
                        // Creating Contact Adapter For Paging Footer Span Count
                        val contactAdapter = adapter.withLoadStateFooter(
                            footer = LoadStateAdapter { adapter.retry() }
                        )

                        recyclerViewNowPlayingSeries.layoutManager =
                            GridLayoutManager(context, 2).apply {
                                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                    override fun getSpanSize(position: Int): Int {
                                        return if (contactAdapter.getItemViewType(position) in
                                            arrayOf(1)
                                        ) spanCount else 1
                                    }

                                }
                            }

                        recyclerViewNowPlayingSeries.adapter = contactAdapter
                        adapter.submitData(lifecycle, response)

                        adapter.loadStateFlow.collectLatest { loadStates ->
                            when (loadStates.refresh) {
                                is LoadState.Loading -> {
                                    nowPlayingSeriesLoading.visible()
                                    nowPlayingSeriesLoading.startShimmer()
                                    recyclerViewNowPlayingSeries.gone()
                                }
                                is LoadState.NotLoading -> {
                                    nowPlayingSeriesLoading.gone()
                                    nowPlayingSeriesLoading.stopShimmer()
                                    recyclerViewNowPlayingSeries.visible()
                                }
                                is LoadState.Error -> {
                                    requireActivity().showToast(
                                        getString(R.string.error),
                                        (loadStates.refresh as LoadState.Error).error.localizedMessage
                                            ?: "Error",
                                        www.sanju.motiontoast.MotionToastStyle.ERROR
                                    )
                                }

                            }
                        }
                    }
                }

            }
        }
    }

}