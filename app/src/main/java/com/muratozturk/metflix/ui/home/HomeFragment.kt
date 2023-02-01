package com.muratozturk.metflix.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.databinding.FragmentHomeBinding
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var timer: MyCountDownTimer? = null

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(::onClickItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            with(viewModel) {
                collectData()
                initUI()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI() {
        with(binding) {
            with(viewModel) {

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
//                                LoadingScreen.displayLoading(requireContext(), false)
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
//                                LoadingScreen.hideLoading()

                                val pagerAdapter =
                                    ViewPagerAdapter(response.data as ArrayList<MovieUI>)
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
                        adapter.submitData(
                            lifecycle,
                            response
                        )
                        binding.recyclerViewNowPlayingMovies.adapter = adapter
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingSeries.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
//                                LoadingScreen.displayLoading(requireContext(), false)
                            }
                            is Resource.Error -> {
//                                LoadingScreen.hideLoading()
                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
//                                LoadingScreen.hideLoading()

                                val adapter =
                                    SeriesAdapter(response.data as ArrayList<SerieUI>)
                                binding.recyclerViewNowPlayingSeries.adapter = adapter

                            }
                        }
                    }
                }
            }
        }
    }

    private fun onClickItem(movie: Int) {

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
        Timber.d("onStopTimer")
        if (timer != null) timer!!.cancel()
    }

    private fun onUserInteraction() {
        Timber.d("onUserInteraction")
        if (timer != null) {
            timer!!.cancel()
            timer!!.start()
        }
    }
}