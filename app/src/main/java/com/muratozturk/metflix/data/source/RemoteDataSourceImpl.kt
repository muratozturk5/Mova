package com.muratozturk.metflix.data.source

import com.muratozturk.metflix.data.model.remote.movies.MovieResponse
import com.muratozturk.metflix.data.model.remote.series.SerieResponse
import com.muratozturk.metflix.data.service.RemoteService
import com.muratozturk.metflix.domain.source.DataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieService: RemoteService) :
    DataSource.Remote {
    override suspend fun getPopularMovies(): MovieResponse = movieService.getPopularMovies()
    override suspend fun getNowPlayingMovies(): MovieResponse = movieService.getNowPlayingMovies()
    override suspend fun getNowPlayingSeries(): SerieResponse = movieService.getNowPlayingSeries()

}