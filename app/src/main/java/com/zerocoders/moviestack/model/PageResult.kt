package com.zerocoders.moviestack.model

interface PageResult<T> {
    val page: Int
    val results: List<T>
    val totalResults: Int
    val totalPages: Int
}