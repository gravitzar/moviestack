package com.zerocoders.moviestack.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HomeMovie(
        route = MovieHome.route,
        label = "Movie",
        icon = Icons.Rounded.Movie
    ),

    MovieDiscover(
        route = DiscoverMovie.route,
        label = "Discover",
        icon = Icons.Rounded.Search
    )
}