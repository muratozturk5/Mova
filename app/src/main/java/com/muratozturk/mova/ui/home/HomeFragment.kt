package com.muratozturk.mova.ui.home

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.databinding.FragmentHomeBinding
import com.muratozturk.mova.domain.model.MovieUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var timer: MyCountDownTimer? = null

    private val adapterNowPlayingMovies: MovieAdapter by lazy { MovieAdapter(::onClickMovieItem) }
    private val adapterNowPlayingSeries: SerieAdapter by lazy { SerieAdapter(::onClickSerieItem) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectData()
        initUI()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI() {
        with(binding) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            viewpagerPopularMovies.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> onUserInteraction()
                }

                v?.onTouchEvent(event) ?: true
            }

            seeAllNowPlayingMovies.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToNowPlayingMoviesFragment()
                findNavController().navigate(action)
            }

            seeAllNowPlayingSeries.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToNowPlayingSeriesFragment()
                findNavController().navigate(action)
            }

            searchBtn.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToExploreFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    popularMovies.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
                                popularMoviesLoading.visible()
                                popularMoviesLoading.startShimmer()
                            }
                            is Resource.Error -> {
                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                popularMoviesLoading.gone()
                                popularMoviesLoading.stopShimmer()
                                val pagerAdapter =
                                    ViewPagerAdapter(
                                        response.data as ArrayList<MovieUI>,
                                        ::onClickMovieItem,
                                        ::onClickMovieItemPlay,
                                        ::onClickMovieItemAddList
                                    )
                                viewpagerPopularMovies.apply {
                                    setScrollDurationFactor(4)
                                    setPageTransformer(
                                        true,
                                        parallaxPageTransformer(
                                            R.id.movieActions
                                        )
                                    )
                                    adapter = pagerAdapter
                                }
                                pageSwitcher(response.data)

                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingMovies.collectLatest { response ->

                        recyclerViewNowPlayingMovies.adapter = adapterNowPlayingMovies
                        adapterNowPlayingMovies.submitData(lifecycle, response)


                        adapterNowPlayingMovies.loadStateFlow.collectLatest { loadStates ->
                            when (loadStates.refresh) {
                                is LoadState.Loading -> {
                                    nowPlayingMoviesLoading.visible()
                                    nowPlayingMoviesLoading.startShimmer()
                                    recyclerViewNowPlayingMovies.gone()
                                }
                                is LoadState.NotLoading -> {
                                    nowPlayingMoviesLoading.gone()
                                    nowPlayingMoviesLoading.stopShimmer()
                                    recyclerViewNowPlayingMovies.visible()
                                }
                                is LoadState.Error -> {
                                    requireActivity().showToast(
                                        getString(R.string.error),
                                        (loadStates.refresh as LoadState.Error).error.localizedMessage
                                            ?: "Error",
                                        MotionToastStyle.ERROR
                                    )
                                }

                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingSeries.collectLatest { response ->
                        recyclerViewNowPlayingSeries.adapter = adapterNowPlayingSeries
                        adapterNowPlayingSeries.submitData(lifecycle, response)

                        adapterNowPlayingSeries.loadStateFlow.collectLatest { loadStates ->
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

    private fun onClickMovieItem(movie: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie, MediaTypeEnum.MOVIE)
        findNavController().navigate(action)
    }

    private fun onClickMovieItemPlay(movie: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToVideoPlayerFragment(
                videoId = null,
                id = movie,
                mediaType = MediaTypeEnum.MOVIE
            )
        findNavController().navigate(action)
    }

    private fun onClickMovieItemAddList(movie: Int, isBookmarked: Boolean, bookmark: Bookmark) {
        with(viewModel) {
            if (isBookmarked) removeBookmark(movie)
            else addBookmark(bookmark)
        }
    }

    private fun onClickSerieItem(serie: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(serie, MediaTypeEnum.SERIE)
        findNavController().navigate(action)
    }

    private fun pageSwitcher(list: MutableList<MovieUI>) {
        with(binding) {
            timer = MyCountDownTimer(5000, 5000) {
                try {
                    if (list.size - 1 == viewpagerPopularMovies.currentItem) viewpagerPopularMovies.currentItem =
                        0
                    else viewpagerPopularMovies.currentItem = viewpagerPopularMovies.currentItem + 1
                    timer!!.start()
                } catch (t: Throwable) {
                    timer!!.cancel()
                }
            }
            timer!!.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        onStopTimer()
    }


    private fun onStopTimer() {
        if (timer != null) timer!!.cancel()
    }

    private fun onUserInteraction() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.start()
        }
    }
}