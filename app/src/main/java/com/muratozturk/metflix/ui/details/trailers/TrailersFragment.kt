package com.muratozturk.metflix.ui.details.trailers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.common.enums.MediaTypeEnum
import com.muratozturk.metflix.databinding.FragmentTrailersBinding
import com.muratozturk.metflix.ui.details.DetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class TrailersFragment : Fragment(R.layout.fragment_trailers) {
    private val binding by viewBinding(FragmentTrailersBinding::bind)
    private val viewModel: TrailersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(binding) {
            with(viewModel) {

            }
        }
    }

    private fun onTrailerClick(videoId: String) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToVideoPlayerFragment(
            videoId,
            MediaTypeEnum.MOVIE,
            0
        )
        findNavController().navigate(action)
    }

    fun collectData() {
        with(viewModel) {
            with(binding) {

                lifecycleScope.launchWhenCreated {
                    trailers.collectLatest { response ->

                        when (response) {
                            is Resource.Loading -> {
                                trailersRecycler.gone()
                            }
                            is Resource.Error -> {
                                trailersRecycler.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                trailersRecycler.visible()

                                val trailersAdapter =
                                    TrailersAdapter(response.data)
                                trailersRecycler.adapter = trailersAdapter
                                trailersAdapter.onClick = ::onTrailerClick
                            }
                        }
                    }
                }


            }
        }
    }

    companion object {
        fun createBundle(id: Int, mediaType: MediaTypeEnum) =
            TrailersFragment().apply {
                arguments = Bundle().apply {
                    Timber.e("id: $id mediaType: $mediaType")
                    putInt(Constants.Arguments.ID, id)
                    putSerializable(Constants.Arguments.MEDIA_TYPE, mediaType)
                }
            }
    }
}