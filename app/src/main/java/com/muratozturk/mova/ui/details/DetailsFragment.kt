package com.muratozturk.mova.ui.details

import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.databinding.FragmentDetailsBinding
import com.muratozturk.mova.domain.model.MovieDetailsUI
import com.muratozturk.mova.domain.model.SerieDetailsUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private var isMovieSerieBookmarked: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    private fun onClickCredit(id: Int) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToPersonDetailFragment(id)
        findNavController().navigate(action)
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

                                    addListener(movie = this, serie = null)
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
                                val creditsAdapter = CreditsAdapter(response.data)
                                recyclerViewCasts.adapter = creditsAdapter
                                creditsAdapter.onClickHigh = ::onClickCredit
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

                                    addListener(movie = null, serie = this)
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
                                val creditsAdapter = CreditsAdapter(response.data)
                                recyclerViewCasts.adapter = creditsAdapter
                                creditsAdapter.onClickHigh = ::onClickCredit
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
                    isBookmarked.collectLatest { response ->
                        when (response) {
                            is Resource.Success -> {
                                if (response.data) {
                                    bookmarkBtn.setImageResource(R.drawable.bookmark_curved_filled)
                                    bookmarkBtn.imageTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.red
                                        )
                                    )
                                } else {
                                    bookmarkBtn.setImageResource(R.drawable.bookmark_curved)
                                    bookmarkBtn.imageTintList =
                                        ColorStateList.valueOf(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.text_color
                                            )
                                        )
                                }
                                isMovieSerieBookmarked = response.data
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

    private fun addListener(movie: MovieDetailsUI?, serie: SerieDetailsUI?) {
        with(binding) {
            with(viewModel) {
                var bookmark: Bookmark? = null
                var download: Download? = null
                var stareText: String? = null

                when (args.mediaType) {
                    MediaTypeEnum.MOVIE -> {
                        movie?.let { movie ->
                            bookmark = Bookmark(
                                movie.id,
                                movie.title,
                                "",
                                movie.posterPath ?: "",
                                movie.voteAverage,
                                args.mediaType
                            )

                            download = Download(
                                movie.id,
                                movie.title,
                                "",
                                movie.backdropPath ?: "",
                                movie.runtime ?: 0,
                                type = args.mediaType
                            )

                            stareText = "${movie.title} - ${movie.homepage}"
                        }
                    }
                    MediaTypeEnum.SERIE -> {
                        serie?.let { serie ->
                            bookmark = Bookmark(
                                serie.id,
                                serie.name,
                                "",
                                serie.posterPath,
                                serie.voteAverage,
                                args.mediaType
                            )

                            download = Download(
                                serie.id,
                                serie.name,
                                "",
                                serie.backdropPath,
                                getRandomRuntime(),
                                type = args.mediaType
                            )

                            stareText = "${serie.name} - ${serie.homepage}"
                        }
                    }
                }

                bookmarkBtn.setOnClickListener {
                    if (isMovieSerieBookmarked) {
                        removeBookmark(bookmark!!.id)
                        isMovieSerieBookmarked = false
                        bookmarkBtn.setImageResource(R.drawable.bookmark_curved)
                        bookmarkBtn.imageTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.text_color
                            )
                        )
                    } else {
                        addBookmark(bookmark!!)
                        isMovieSerieBookmarked = true
                        bookmarkBtn.setImageResource(R.drawable.bookmark_curved_filled)
                        bookmarkBtn.imageTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                    }
                }

                shareBtn.setOnClickListener {
                    requireContext().openShareIntent(stareText!!)
                }

                downloadBtn.setOnClickListener {
                    val action =
                        DetailsFragmentDirections.actionDetailsFragmentToDownloadDialogFragment(
                            download!!
                        )
                    findNavController().navigate(action)
                }

            }
        }
    }
}