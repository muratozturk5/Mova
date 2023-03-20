package com.muratozturk.mova.ui.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.muratozturk.mova.R
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.common.showToast
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle


@AndroidEntryPoint
class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {

    private val binding by viewBinding(FragmentVideoPlayerBinding::bind)
    private val viewModel: VideoPlayerViewModel by viewModels()
    private val args: VideoPlayerFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }


    fun initUI() {
        with(binding) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN



            lifecycle.addObserver(youtubePlayerView)
            args.videoId?.let {
                youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {

                        youTubePlayer.loadVideo(
                            it,
                            0f
                        )
                    }
                })

            }

        }
    }

    fun collectData() {
        with(viewModel) {
            with(binding) {
                lifecycleScope.launchWhenCreated {
                    trailers.collectLatest { response ->

                        when (response) {
                            is Resource.Loading -> {
                            }
                            is Resource.Error -> {
                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )
                            }
                            is Resource.Success -> {
                                if (args.videoId == null && response.data.isNotEmpty()) {
                                    youtubePlayerView.addYouTubePlayerListener(object :
                                        AbstractYouTubePlayerListener() {
                                        var videoIndex = 0
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            youTubePlayer.loadVideo(
                                                response.data[videoIndex].key,
                                                0f
                                            )
                                        }

                                        override fun onStateChange(
                                            youTubePlayer: YouTubePlayer,
                                            state: PlayerConstants.PlayerState
                                        ) {
                                            super.onStateChange(youTubePlayer, state)
                                            if (state == PlayerConstants.PlayerState.ENDED) {
                                                if (videoIndex < response.data.size - 1) {
                                                    videoIndex++
                                                    youTubePlayer.loadVideo(
                                                        response.data[videoIndex].key,
                                                        0f
                                                    )
                                                }
                                            }
                                        }
                                    })

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}