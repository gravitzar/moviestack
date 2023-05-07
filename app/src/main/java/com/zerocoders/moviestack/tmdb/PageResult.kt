package com.zerocoders.moviestack.tmdb

interface PageResult<T> {
    val page: Int
    val results: List<T>
    val totalResults: Int
    val totalPages: Int
}