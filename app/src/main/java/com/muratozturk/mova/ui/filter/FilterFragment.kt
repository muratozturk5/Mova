package com.muratozturk.mova.ui.filter

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment(R.layout.fragment_filter) {
    private val binding by viewBinding(FragmentFilterBinding::bind)
    private val viewModel: FilterViewModel by viewModels()
    private var returnFilterResult = FilterResult()
    private val args: FilterFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let {
            if (window != null) {
                window.statusBarColor = it.getColor(android.R.color.transparent)
            }
        }
        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.skipCollapsed = true

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnFilterResult = args.filterModel
        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            with(viewModel) {

                if (returnFilterResult.type == MediaTypeEnum.MOVIE) {
                    movieRadioBtn.isChecked = true
                } else if (returnFilterResult.type == MediaTypeEnum.SERIE) {
                    serieRadioBtn.isChecked = true
                }

                when (returnFilterResult.sortBy) {
                    Constants.SortBy.POPULARITY -> popularityRadioBtn.isChecked = true
                    Constants.SortBy.RELEASE_DATE -> releaseDateRadioBtn.isChecked = true
                    Constants.SortBy.VOTE_AVERAGE -> voteAverageRadioBtn.isChecked = true
                    Constants.SortBy.VOTE_COUNT -> voteCountRadioBtn.isChecked = true
                }

                includeAdultToggleBtn.isChecked = returnFilterResult.includeAdult

                if (movieRadioBtn.isChecked) {
                    getMovieGenres()
                } else if (serieRadioBtn.isChecked) {
                    getSerieGenres()
                } else {
                    getMovieGenres()
                }

                movieRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        returnFilterResult.selectedGenreList = mutableListOf()
                        getMovieGenres()
                    }
                }

                serieRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        returnFilterResult.selectedGenreList = mutableListOf()
                        getSerieGenres()
                    }
                }

                applyButton.setOnClickListener {
                    createFilterResult()
                    setFragmentResult(
                        Constants.Arguments.POP_UP,
                        bundleOf(Constants.Arguments.FILTER_RESULT to returnFilterResult)
                    )
                    findNavController().popBackStack()
                }
                resetButton.setOnClickListener {
                    returnFilterResult = FilterResult()
                    initUI()
                }
            }
        }
    }

    private fun createFilterResult() {
        with(binding) {

            if (movieRadioBtn.isChecked) {
                returnFilterResult.type = MediaTypeEnum.MOVIE
            } else if (serieRadioBtn.isChecked) {
                returnFilterResult.type = MediaTypeEnum.SERIE
            } else {
                returnFilterResult.type = MediaTypeEnum.MOVIE
            }

            returnFilterResult.includeAdult = includeAdultToggleBtn.isChecked

            returnFilterResult.sortBy = when (sortByRadioGroup.checkedRadioButtonId) {
                R.id.popularityRadioBtn -> Constants.SortBy.POPULARITY
                R.id.releaseDateRadioBtn -> Constants.SortBy.RELEASE_DATE
                R.id.voteAverageRadioBtn -> Constants.SortBy.VOTE_AVERAGE
                R.id.voteCountRadioBtn -> Constants.SortBy.VOTE_COUNT
                else -> Constants.SortBy.POPULARITY
            }

        }

    }

    private fun collectData() {
        with(binding) {
            with(viewModel) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    genres.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
                                genresLoading.visible()
                                genresLoading.startShimmer()
                                recyclerViewGenres.gone()
                            }
                            is Resource.Error -> {
                                recyclerViewGenres.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                genresLoading.gone()
                                genresLoading.stopShimmer()
                                recyclerViewGenres.visible()

                                returnFilterResult.genreList = response.data
                                val filterAdapter =
                                    FilterAdapter(returnFilterResult)
                                recyclerViewGenres.adapter = filterAdapter
                                filterAdapter.filterResultHigh = {
                                    returnFilterResult = it
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}