package com.muratozturk.mova.ui.person.images

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.ImageTypeEnum
import com.muratozturk.mova.databinding.FragmentPersonImagesBinding
import com.muratozturk.mova.domain.model.ImageUI
import com.muratozturk.mova.ui.person.PersonDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class PersonImagesFragment : Fragment(R.layout.fragment_person_images) {
    private val viewModel: PersonImagesViewModel by viewModels()
    private val binding by viewBinding(FragmentPersonImagesBinding::bind)

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

    private fun onClick(list: List<ImageUI>, position: Int) {
        val action =
            PersonDetailFragmentDirections.actionPersonDetailFragmentToPreviewImagesFragment(
                list.toTypedArray(),
                position,
                ImageTypeEnum.POSTER
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
                                    PersonImagesAdapter(response.data)
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
        fun createBundle(id: Int) =
            PersonImagesFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.Arguments.ID, id)
                }
            }
    }
}