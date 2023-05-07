package com.zerocoders.moviestack.network

import com.zerocoders.moviestack.tmdb.CreditResult
import com.zerocoders.moviestack.tmdb.Movie
import com.zerocoders.moviestack.tmdb.MoviePageResult
import com.zerocoders.moviestack.tmdb.VideoResult

interface RemoteDataSource {

    suspend fun getPopularMovies(page: Int = 1): MoviePageResult?

    suspend fun getTopRatedMovies(page: Int = 1): MoviePageResult?

    suspend fun getUpComingMovies(page: Int = 1): MoviePageResult?

    suspend fun getNowPlayingMovies(page: Int = 1): MoviePageResult?

    suspend fun getSimilarMovies(movieId: Int = 1): MoviePageResult?

    suspend fun getMovie(movieId: Int = 0): Movie?

    suspend fun getLatestMovie(): Movie?

    suspend fun getMovieVideos(movieId: Int = 0): VideoResult?

    suspend fun getMovieCredits(movieId: Int = 0): CreditResult?

    suspend fun discoverMovies(page: Int = 1): MoviePageResult?
}