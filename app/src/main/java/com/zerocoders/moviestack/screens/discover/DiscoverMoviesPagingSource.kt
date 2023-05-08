package com.zerocoders.moviestack.screens.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zerocoders.moviestack.model.Movie
import com.zerocoders.moviestack.network.RemoteDataSource
import javax.inject.Inject

class DiscoverMoviesPagingSource @Inject constructor(
    private val tmdbRemoteDataSource: RemoteDataSource
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val discoverMoviesResult = tmdbRemoteDataSource.discoverMovies(page)
            LoadResult.Page(
                data = discoverMoviesResult?.results ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (discoverMoviesResult?.results.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}