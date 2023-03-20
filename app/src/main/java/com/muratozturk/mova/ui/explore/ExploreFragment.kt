package com.muratozturk.mova.ui.explore

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.FilterResult
import com.muratozturk.mova.databinding.FragmentExploreBinding
import com.muratozturk.mova.ui.home.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val binding by viewBinding(FragmentExploreBinding::bind)
    private val viewModel: ExploreViewModel by viewModels()
    private val movieAdapter: ExploreMovieAdapter by lazy { ExploreMovieAdapter(::onClickMovieItem) }
    private val serieAdapter: ExploreSeriesAdapter by lazy { ExploreSeriesAdapter(::onClickSerieItem) }
    private var returnFilterResult = FilterResult()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterResultController()
        filterResult()
        initUI()
        collectData()
    }

    private fun initUI() {
        with(binding) {
            searchEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                searchEditText.changeFocusedInputTint(hasFocus)
            }
            searchEditText.addTextChangedListener { editable ->
                editable?.let {
                    if (it.isNotEmpty()) {
                        searchController(it.toString())
                    } else {
                        filterResultController()
                    }
                }
            }
            filterButton.setOnClickListener {
                val action = ExploreFragmentDirections.actionExploreFragmentToFilterFragment(
                    returnFilterResult
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun onClickMovieItem(id: Int) {
        val action = ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(
            id,
            MediaTypeEnum.MOVIE
        )
        findNavController().navigate(action)
    }

    private fun onClickSerieItem(id: Int) {
        val action = ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(
            id,
            MediaTypeEnum.SERIE
        )
        findNavController().navigate(action)
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    discoverMovies.collectLatest { response ->

                        // Set Adapter If Type Is Movie
                        if (returnFilterResult.type == MediaTypeEnum.MOVIE) {

                            // Creating Contact Adapter For Paging Footer Span Count
                            val contactAdapter = movieAdapter.withLoadStateFooter(
                                footer = LoadStateAdapter { movieAdapter.retry() }
                            )

                            recyclerDiscover.layoutManager =
                                GridLayoutManager(context, 2).apply {
                                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                        override fun getSpanSize(position: Int): Int {
                                            return if (contactAdapter.getItemViewType(position) in
                                                arrayOf(1)
                                            ) spanCount else 1
                                        }

                                    }
                                }

                            recyclerDiscover.adapter = contactAdapter

                            movieAdapter.submitData(lifecycle, response)


                            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                                when (loadStates.refresh) {
                                    is LoadState.Loading -> {
                                        discoverLoading.visible()
                                        discoverLoading.startShimmer()
                                        recyclerDiscover.gone()
                                    }
                                    is LoadState.NotLoading -> {
                                        discoverLoading.gone()
                                        discoverLoading.stopShimmer()
                                        recyclerDiscover.visible()
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
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    discoverSeries.collectLatest { response ->
                        // Set Adapter If Type Is Series
                        if (returnFilterResult.type == MediaTypeEnum.SERIE) {
                            // Creating Contact Adapter For Paging Footer Span Count
                            val contactAdapter = serieAdapter.withLoadStateFooter(
                                footer = LoadStateAdapter { serieAdapter.retry() }
                            )

                            recyclerDiscover.layoutManager =
                                GridLayoutManager(context, 2).apply {
                                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                        override fun getSpanSize(position: Int): Int {
                                            return if (contactAdapter.getItemViewType(position) in
                                                arrayOf(1)
                                            ) spanCount else 1
                                        }

                                    }
                                }


                            recyclerDiscover.adapter = contactAdapter



                            recyclerDiscover.adapter = contactAdapter
                            serieAdapter.submitData(lifecycle, response)

                            serieAdapter.loadStateFlow.collectLatest { loadStates ->
                                when (loadStates.refresh) {
                                    is LoadState.Loading -> {
                                        discoverLoading.visible()
                                        discoverLoading.startShimmer()
                                        recyclerDiscover.gone()
                                    }
                                    is LoadState.NotLoading -> {
                                        discoverLoading.gone()
                                        discoverLoading.stopShimmer()
                                        recyclerDiscover.visible()
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
                }
            }
        }
    }

    private fun fetchFilters() {
        with(binding) {
            searchEditText.text.clear()
            searchEditText.clearFocus()

            filtersLoading.visible()
            filtersLoading.startShimmer()

            val filterList = mutableListOf<String>()

            if (returnFilterResult.type == MediaTypeEnum.MOVIE) {
                filterList.add(resources.getString(R.string.movie))
            } else {
                filterList.add(resources.getString(R.string.tv_series))
            }

            if (returnFilterResult.includeAdult) {
                filterList.add(resources.getString(R.string.include_adult))
            }

            returnFilterResult.selectedGenreList.forEach {
                filterList.add(it.name)
            }

            val filterAdapter =
                SelectedFiltersAdapter(filterList)
            recyclerViewFilters.adapter = filterAdapter

            filtersLoading.gone()
            filtersLoading.stopShimmer()
            recyclerViewFilters.visible()

        }
    }

    private fun filterResultController() {
        if (returnFilterResult.type == MediaTypeEnum.MOVIE) {
            viewModel.getDiscoverMovies(returnFilterResult)
        } else {
            viewModel.getDiscoverSeries(returnFilterResult)
        }

        fetchFilters()
    }

    private fun searchController(query: String) {
        if (returnFilterResult.type == MediaTypeEnum.MOVIE) {
            viewModel.getSearchMovie(query, returnFilterResult.includeAdult)
        } else {
            viewModel.getSearchSerie(query, returnFilterResult.includeAdult)
        }

    }

    private fun filterResult() {
        setFragmentResultListener(Constants.Arguments.POP_UP) { _, bundle ->

            returnFilterResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(Constants.Arguments.FILTER_RESULT, FilterResult::class.java)
                    ?: FilterResult()
            } else {
                @Suppress("DEPRECATION") (bundle.getParcelable(Constants.Arguments.FILTER_RESULT)
                    ?: FilterResult())
            }


            filterResultController()
        }
    }
}