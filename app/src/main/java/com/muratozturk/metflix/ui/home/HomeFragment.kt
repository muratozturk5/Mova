package com.muratozturk.metflix.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.databinding.FragmentHomeBinding
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var timer: MyCountDownTimer? = null

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

                viewpagerPopularMovies.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        when (event?.action) {
                            MotionEvent.ACTION_DOWN -> onUserInteraction()
                        }

                        return v?.onTouchEvent(event) ?: true
                    }
                })
            }
        }
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    popularMovies.collect { response ->
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
                    nowPlayingMovies.collect { response ->
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
                                LoadingScreen.hideLoading()

                                val adapter =
                                    MoviesAdapter(response.data as ArrayList<MovieUI>)
                                binding.recyclerViewNowPlayingMovies.adapter = adapter

                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    nowPlayingSeries.collect { response ->
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

    fun onUserInteraction() {
        Timber.d("onUserInteraction")
        if (timer != null) {
            timer!!.cancel()
            timer!!.start()
        }
    }
}