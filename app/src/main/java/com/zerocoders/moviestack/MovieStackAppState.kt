package com.zerocoders.moviestack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zerocoders.moviestack.navigation.BottomNavigationItem
import com.zerocoders.moviestack.navigation.DiscoverMovie
import com.zerocoders.moviestack.navigation.MovieHome

@Composable
fun rememberMovieStackAppState(
    navController: NavHostController = rememberNavController()
): MovieStackAppState {
    return remember(navController) {
        MovieStackAppState(navController)
    }
}

class MovieStackAppState(val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = when (currentDestination?.route) {
            MovieHome.route, DiscoverMovie.route -> true
            else -> false
        }

    val bottomNavItems: List<BottomNavigationItem> = BottomNavigationItem.values().asList()
}