package com.muratozturk.mova.ui.details.images

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.databinding.FragmentImagesBinding
import com.muratozturk.mova.domain.model.ImageUI
import com.muratozturk.mova.ui.details.DetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class ImagesFragment : Fragment(R.layout.fragment_images) {
    private val binding by viewBinding(FragmentImagesBinding::bind)
    private val viewModel: ImagesViewModel by viewModels()
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

    fun onClick(list: List<ImageUI>, position: Int) {
        val action =
            DetailsFragmentDirections.actionDetailsFragmentToPreviewImagesFragment(
                list.toTypedArray(),
                position,
                ImageTypeEnum.BACKDROP
            )
        findNavController().navigate(action)
    }

    fun collectData() {
        with(viewModel) {
            with(binding) {

                lifecycleScope.launchWhenCreated {
                    images.collectLatest { response ->

                        when (response) {
                            is Resource.Loading -> {
                                imagesRecycler.gone()
                            }
                            is Resource.Error -> {
                                imagesRecycler.gone()

                                requireActivity().showToast(
                                    getString(com.muratozturk.mova.R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                imagesRecycler.visible()

                                val imagesAdapter =
                                    ImagesAdapter(response.data)
                                imagesRecycler.adapter = imagesAdapter
                                imagesAdapter.onClick = ::onClick

                            }
                        }
                    }
                }


            }
        }
    }

    companion object {
        fun createBundle(id: Int, mediaType: MediaTypeEnum) =
            ImagesFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.Arguments.ID, id)
                    putSerializable(Constants.Arguments.MEDIA_TYPE, mediaType)
                }
            }
    }
}