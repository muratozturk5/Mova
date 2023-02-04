package com.muratozturk.metflix.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muratozturk.metflix.common.Constants.STARTING_PAGE
import com.muratozturk.metflix.common.MovieRequestOptionsMapper
import com.muratozturk.metflix.common.enums.SerieEnum
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.data.model.remote.series.Serie
import com.muratozturk.metflix.data.service.MetflixService
import okio.IOException
import retrofit2.HttpException

class SeriePagingSource(
    private val movieService: MetflixService,
    private val serieEnum: SerieEnum,
    private val searchQuery: String = "",
    movieRequestOptionsMapper: MovieRequestOptionsMapper,
    filterResult: FilterResult? = null,
    private val includeAdult: Boolean = false

) : PagingSource<Int, Serie>() {
    private val options = movieRequestOptionsMapper.map(filterResult)


    override fun getRefreshKey(state: PagingState<Int, Serie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Serie> {
        val page = params.key ?: STARTING_PAGE

        return try {
            val response =
                when (serieEnum) {
                    SerieEnum.DISCOVER_SERIES -> {
                        movieService.getDiscoverSeries(
                            page = page,
                            options = options
                        )
                    }
                    SerieEnum.NOW_PLAYING_SERIES -> {
                        movieService.getNowPlayingSeries(page = page)
                    }
                    SerieEnum.SEARCH_SERIES -> {
                        movieService.getSearchSerie(
                            page = page,
                            query = searchQuery,
                            includeAdult = includeAdult
                        )
                    }
                }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(throwable = e)
        } catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        }
    }
}