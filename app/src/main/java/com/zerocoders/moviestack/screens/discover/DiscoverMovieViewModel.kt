package com.zerocoders.moviestack.screens.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zerocoders.moviestack.SessionPreferenceManager
import com.zerocoders.moviestack.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    sessionPreferenceManager: SessionPreferenceManager,
    moviesPagingSource: DiscoverMoviesPagingSource
) : ViewModel() {
    val isLoggedIn = sessionPreferenceManager.isLoggedInFlow

    val movies: Flow<PagingData<Movie>> = Pager(
        pagingSourceFactory = { moviesPagingSource },
        config = PagingConfig(pageSize = 12)
    ).flow.cachedIn(viewModelScope)
}