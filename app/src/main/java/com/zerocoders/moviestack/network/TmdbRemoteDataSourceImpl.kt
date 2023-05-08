package com.zerocoders.moviestack.network

import com.zerocoders.moviestack.model.CreditResult
import com.zerocoders.moviestack.model.Movie
import com.zerocoders.moviestack.model.MoviePageResult
import com.zerocoders.moviestack.model.video.VideoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TmdbRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : RemoteDataSource {

    override suspend fun getPopularMovies(page: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/popular") {
                    parameter("language", "en-US")
                    parameter("page", page)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/top_rated") {
                    parameter("language", "en-US")
                    parameter("page", page)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getUpComingMovies(page: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/upcoming") {
                    parameter("language", "en-US")
                    parameter("page", page)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/now_playing") {
                    parameter("language", "en-US")
                    parameter("page", page)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getMovie(movieId: Int): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/$movieId") {
                    parameter("language", "en-US")
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/$movieId/similar") {
                    parameter("language", "en-US")
                    parameter("page", 1)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getLatestMovie(): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/latest") {
                    parameter("language", "en-US")
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getMovieVideos(movieId: Int): VideoResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/$movieId/videos") {
                    parameter("language", "en-US")
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getMovieCredits(movieId: Int): CreditResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/movie/$movieId/credits") {
                    parameter("language", "en-US")
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun discoverMovies(page: Int): MoviePageResult? {
        return withContext(Dispatchers.IO) {
            try {
                client.get("/discover/movie") {
                    parameter("language", "en-US")
                    parameter("page", page)
                }.body()
            } catch (e: Exception) {
                null
            }
        }
    }
}