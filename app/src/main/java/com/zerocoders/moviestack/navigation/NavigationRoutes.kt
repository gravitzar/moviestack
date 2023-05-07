package com.zerocoders.moviestack.navigation

sealed class Destination(val route: String)

object Login : Destination("login")
object SingUp : Destination("signup")
object MovieHome : Destination("movie_home")
object DiscoverMovie : Destination("discover_movie")

object MovieDetail : Destination("movie_detail/{id}") {
    fun createRoute(id: Int) = "movie_detail/$id"
}

object VideoTrailer : Destination("video_trailer/{id}") {
    fun createRoute(id: String) = "video_trailer/$id"
}