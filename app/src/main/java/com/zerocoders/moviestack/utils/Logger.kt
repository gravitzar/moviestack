package com.zerocoders.moviestack.utils

import android.util.Log

fun Any.debug(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun Any.debug(message: String, tr: Throwable) {
    Log.d(this::class.java.simpleName, message, tr)
}

fun Any.info(message: String) {
    Log.i(this::class.java.simpleName, message)
}

fun Any.info(message: String, tr: Throwable) {
    Log.i(this::class.java.simpleName, message, tr)
}

fun Any.warn(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.warn(message: String, tr: Throwable) {
    Log.w(this::class.java.simpleName, message, tr)
}

fun Any.error(message: String) {
    Log.e(this::class.java.simpleName, message)
}

fun Any.error(message: String, tr: Throwable) {
    Log.e(this::class.java.simpleName, message, tr)
}