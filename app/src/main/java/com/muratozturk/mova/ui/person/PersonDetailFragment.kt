package com.muratozturk.mova.ui.person

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.databinding.FragmentPersonDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class PersonDetailFragment : Fragment(R.layout.fragment_person_detail) {
    private val binding by viewBinding(FragmentPersonDetailBinding::bind)
    private val viewModel: PersonDetailViewModel by viewModels()
    private val args: PersonDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(binding) {

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewPager.adapter =
                PersonViewPagerAdapter(childFragmentManager, lifecycle, args.id)


            viewModel.getPersonDetails(args.id)

            val tabsArray = arrayOf(
                resources.getString(R.string.images),
                resources.getString(R.string.movies),
                resources.getString(R.string.tv_series)
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabsArray[position]
            }.attach()
        }
    }

    fun collectData() {
        with(viewModel) {
            with(binding) {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    personDetails.collectLatest { response ->
                        when (response) {
                            is Resource.Success -> {
                                with(response.data)
                                {

                                    detailsLoading.gone()
                                    imageLayout.visible()
                                    toolbar.visible()
                                    contentLayout.visible()


                                    imageIv.loadImage(
                                        this.profilePath,
                                        imageTypeEnum = com.muratozturk.mova.common.enums.ImageTypeEnum.CREDIT
                                    )

                                    nameTv.text = this.name
                                    birthdayTv.text = getReformatDate(this.birthday)
                                    placeOfBirthTv.text = this.placeOfBirth
                                    biographyTv.text = this.biography


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
            }
        }
    }
}