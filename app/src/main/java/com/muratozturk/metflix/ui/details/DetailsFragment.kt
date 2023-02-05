package com.muratozturk.metflix.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.databinding.FragmentDetailsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(binding) {
            Timber.d("initUI: ${args.mediaType}")

        }
    }

    fun collectData() {
        with(binding) {
            with(viewModel) {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    movieDetails.collectLatest { response ->
                        when (response) {
                            is Resource.Success -> {
                                with(response.data)
                                {
                                    if (backdropPath != null) {
                                        backDropIv.loadImage(backdropPath, isPoster = false)
                                    }
                                    titleTv.text = this.title
                                    overviewTv.text = this.overview
                                    voteAverageTv.text = this.voteAverage.format(1)
                                    yearTv.text = getReformatDate(this.releaseDate)

                                    val genresAdapter = GenresAdapter(this.genres)
                                    recyclerViewGenres.adapter = genresAdapter

                                    when (args.mediaType) {
                                        MediaTypeEnum.MOVIE -> {
                                            mediaTypeTv.text = resources.getString(R.string.movie)
                                        }
                                        MediaTypeEnum.SERIE -> {
                                            mediaTypeTv.text =
                                                resources.getString(R.string.tv_series)
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )
                            }
                            is Resource.Loading -> {
                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    movieCredits.collectLatest { response ->
                        when (response) {
                            is Resource.Success -> {
                                val genresAdapter = CreditsAdapter(response.data)
                                recyclerViewCasts.adapter = genresAdapter
                            }
                            is Resource.Error -> {
                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )
                            }
                            is Resource.Loading -> {
                            }
                        }
                    }
                }
            }
        }
    }
}