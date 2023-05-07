package com.zerocoders.moviestack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zerocoders.moviestack.screens.discover.DiscoverMoviesRoute
import com.zerocoders.moviestack.screens.home.HomeScreenRoute
import com.zerocoders.moviestack.screens.login.LoginScreenRoute
import com.zerocoders.moviestack.screens.movie.MovieDetailScreenRoute
import com.zerocoders.moviestack.screens.signup.SignUpScreenRoute
import com.zerocoders.moviestack.screens.videos.VideoTrailerScreenRoute

@Composable
fun MovieStackNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MovieHome.route
    ) {
        composable(MovieHome.route) {
            HomeScreenRoute(
                onNavigateToMovieDetails = {
                    navController.navigate(MovieDetail.createRoute(it))
                },
                onNavigateToLogin = {
                    navController.navigate(Login.route)
                }
            )
        }
        composable(DiscoverMovie.route) {
            DiscoverMoviesRoute(
                onNavigateToMovieDetails = {
                    navController.navigate(MovieDetail.createRoute(it))
                },
                onNavigateToLogin = {
                    navController.navigate(Login.route)
                }
            )
        }
        composable(Login.route) {
            LoginScreenRoute(
                onLoginSuccess = {
                    navController.popBackStack()
                },
                onNavigateToSignUp = {
                    navController.navigate(SingUp.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                }
            )
        }
        composable(SingUp.route) {
            SignUpScreenRoute(
                onSignUpSuccess = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Login.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                }
            )
        }
        composable(
            MovieDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry: NavBackStackEntry ->
            navBackStackEntry.arguments?.getInt("id")?.let { movieId ->
                MovieDetailScreenRoute(id = movieId, onTrailerVideoClicked = {
                    navController.navigate(VideoTrailer.createRoute(it))
                })
            } ?: navController.navigateUp()
        }
        composable(
            VideoTrailer.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { navBackStackEntry: NavBackStackEntry ->
            navBackStackEntry.arguments?.getString("id")?.let { videoId ->
                VideoTrailerScreenRoute(videoId = videoId)
            } ?: navController.navigateUp()
        }
    }
}