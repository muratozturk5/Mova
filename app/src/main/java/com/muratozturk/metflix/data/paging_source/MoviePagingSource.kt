package com.muratozturk.metflix.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muratozturk.metflix.common.Constants.STARTING_PAGE
import com.muratozturk.metflix.common.enums.MovieEnum
import com.muratozturk.metflix.data.model.remote.movies.Movie
import com.muratozturk.metflix.data.service.MetflixService
import okio.IOException
import retrofit2.HttpException

class MoviePagingSource(
    private val movieService: MetflixService,
    private val movieEnum: MovieEnum
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE

        return try {
            val response =
                when (movieEnum) {
                    MovieEnum.DISCOVER_MOVIES -> {
                        movieService.getDiscoverMovies(
                            page = page
                        )
                    }
                    MovieEnum.NOW_PLAYING_MOVIES -> {
                        movieService.getNowPlayingMovies(page = page)
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