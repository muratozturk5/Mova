package com.muratozturk.mova.ui.details.similar

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.databinding.FragmentSimilarBinding
import com.muratozturk.mova.ui.details.DetailsFragmentDirections
import com.muratozturk.mova.ui.home.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SimilarFragment : Fragment(R.layout.fragment_similar) {

    private val binding by viewBinding(FragmentSimilarBinding::bind)
    private val viewModel: SimilarViewModel by viewModels()
    private val movieAdapter: SimilarMovieAdapter by lazy { SimilarMovieAdapter(::onClickMovieItem) }
    private val serieAdapter: SimilarSeriesAdapter by lazy { SimilarSeriesAdapter(::onClickSerieItem) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mediaType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            arguments?.getSerializable(
                Constants.Arguments.MEDIA_TYPE,
                MediaTypeEnum::class.java
            )

        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(Constants.Arguments.MEDIA_TYPE) as MediaTypeEnum
        }

        collectData(mediaType)
    }

    private fun onClickMovieItem(id: Int) {
        val action = DetailsFragmentDirections.actionDetailsFragmentSelf(
            id,
            MediaTypeEnum.MOVIE
        )
        findNavController().navigate(action)
    }

    private fun onClickSerieItem(id: Int) {
        val action = DetailsFragmentDirections.actionDetailsFragmentSelf(
            id,
            MediaTypeEnum.SERIE
        )
        findNavController().navigate(action)
    }

    private fun collectData(mediaType: MediaTypeEnum?) {
        with(viewModel) {
            with(binding) {


                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    similarMovies.collectLatest { response ->
                        if (mediaType == MediaTypeEnum.MOVIE) {


                            // Creating Contact Adapter For Paging Footer Span Count
                            val contactAdapter = movieAdapter.withLoadStateFooter(
                                footer = LoadStateAdapter { movieAdapter.retry() }
                            )

                            recyclerSimilar.layoutManager =
                                GridLayoutManager(context, 2).apply {
                                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                        override fun getSpanSize(position: Int): Int {
                                            return if (contactAdapter.getItemViewType(position) in
                                                arrayOf(1)
                                            ) spanCount else 1
                                        }

                                    }
                                }

                            recyclerSimilar.adapter = contactAdapter

                            movieAdapter.submitData(lifecycle, response)


                            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                                when (loadStates.refresh) {
                                    is LoadState.Loading -> {
                                        similarLoading.visible()
                                        similarLoading.startShimmer()
                                        recyclerSimilar.gone()
                                    }
                                    is LoadState.NotLoading -> {
                                        similarLoading.gone()
                                        similarLoading.stopShimmer()
                                        recyclerSimilar.visible()
                                    }
                                    is LoadState.Error -> {
                                        requireActivity().showToast(
                                            getString(com.muratozturk.mova.R.string.error),
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

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    similarSeries.collectLatest { response ->
                        if (mediaType == MediaTypeEnum.SERIE) {

                            // Creating Contact Adapter For Paging Footer Span Count
                            val contactAdapter = serieAdapter.withLoadStateFooter(
                                footer = LoadStateAdapter { serieAdapter.retry() }
                            )

                            recyclerSimilar.layoutManager =
                                GridLayoutManager(context, 2).apply {
                                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                        override fun getSpanSize(position: Int): Int {
                                            return if (contactAdapter.getItemViewType(position) in
                                                arrayOf(1)
                                            ) spanCount else 1
                                        }

                                    }
                                }


                            recyclerSimilar.adapter = contactAdapter



                            recyclerSimilar.adapter = contactAdapter
                            serieAdapter.submitData(lifecycle, response)

                            serieAdapter.loadStateFlow.collectLatest { loadStates ->
                                when (loadStates.refresh) {
                                    is LoadState.Loading -> {
                                        similarLoading.visible()
                                        similarLoading.startShimmer()
                                        recyclerSimilar.gone()
                                    }
                                    is LoadState.NotLoading -> {
                                        similarLoading.gone()
                                        similarLoading.stopShimmer()
                                        recyclerSimilar.visible()
                                    }
                                    is LoadState.Error -> {
                                        requireActivity().showToast(
                                            getString(com.muratozturk.mova.R.string.error),
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

    companion object {
        fun createBundle(id: Int, mediaType: MediaTypeEnum) =
            SimilarFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.Arguments.ID, id)
                    putSerializable(Constants.Arguments.MEDIA_TYPE, mediaType)
                }
            }
    }
}