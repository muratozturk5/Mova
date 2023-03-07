package com.muratozturk.metflix.ui.details

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.common.enums.ImageTypeEnum
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.databinding.FragmentDetailsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
            with(viewModel) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                getDetails()

                playBtn.setOnClickListener {
                    val action =
                        DetailsFragmentDirections.actionDetailsFragmentToVideoPlayerFragment(
                            videoId = null,
                            id = args.id,
                            mediaType = args.mediaType
                        )
                    findNavController().navigate(action)
                }

                backButton.setOnClickListener {
                    findNavController().popBackStack()
                }

                viewPager.adapter =
                    ViewPagerAdapter(childFragmentManager, lifecycle, args.id, args.mediaType)


                val tabsArray = arrayOf(
                    resources.getString(R.string.trailers),
                    resources.getString(R.string.images),
                    resources.getString(R.string.smilar)
                )

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = tabsArray[position]
                }.attach()


            }
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

                                    detailsLoading.gone()
                                    imageLayout.visible()
                                    toolbar.visible()
                                    contentLayout.visible()


                                    backDropIv.loadImage(
                                        backdropPath,
                                        imageTypeEnum = ImageTypeEnum.BACKDROP
                                    )

                                    titleTv.text = this.title
                                    overviewTv.text = this.overview
                                    voteAverageTv.text = this.voteAverage.format(1)
                                    yearTv.text = getReformatDate(this.releaseDate)

                                    val genresAdapter = GenresAdapter(this.genres)
                                    recyclerViewGenres.adapter = genresAdapter

                                    StringBuilder().apply {
                                        append(resources.getString(R.string.genres))
                                        append(" : ")
                                        append(genres.joinToString { it.name })
                                        textviewGenres.text = this.toString()
                                    }

                                    when (args.mediaType) {
                                        MediaTypeEnum.MOVIE -> {
                                            mediaTypeTv.text =
                                                resources.getString(R.string.movie)
                                        }
                                        MediaTypeEnum.SERIE -> {
                                            mediaTypeTv.text =
                                                resources.getString(R.string.tv_series)
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                detailsLoading.visible()
                                imageLayout.gone()
                                toolbar.gone()
                                contentLayout.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )
                            }
                            is Resource.Loading -> {
                                detailsLoading.visible()
                                imageLayout.gone()
                                toolbar.gone()
                                contentLayout.gone()
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

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    serieDetail.collectLatest { response ->
                        when (response) {
                            is Resource.Success -> {
                                with(response.data)
                                {

                                    detailsLoading.gone()
                                    imageLayout.visible()
                                    toolbar.visible()
                                    contentLayout.visible()

                                    backDropIv.loadImage(
                                        backdropPath,
                                        imageTypeEnum = ImageTypeEnum.BACKDROP
                                    )
                                    titleTv.text = this.name
                                    overviewTv.text = this.overview
                                    voteAverageTv.text = this.voteAverage.format(1)
                                    yearTv.text = getReformatDate(this.firstAirDate)

                                    val genresAdapter = GenresAdapter(this.genres)
                                    recyclerViewGenres.adapter = genresAdapter

                                    StringBuilder().apply {
                                        append(resources.getString(R.string.genres))
                                        append(" : ")
                                        append(genres.joinToString { it.name })
                                        textviewGenres.text = this.toString()
                                    }

                                    when (args.mediaType) {
                                        MediaTypeEnum.MOVIE -> {
                                            mediaTypeTv.text =
                                                resources.getString(R.string.movie)
                                        }
                                        MediaTypeEnum.SERIE -> {
                                            mediaTypeTv.text =
                                                resources.getString(R.string.tv_series)
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                detailsLoading.visible()
                                imageLayout.gone()
                                toolbar.gone()
                                contentLayout.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )
                            }
                            is Resource.Loading -> {
                                detailsLoading.visible()
                                imageLayout.gone()
                                toolbar.gone()
                                contentLayout.gone()
                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    serieCredits.collectLatest { response ->
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