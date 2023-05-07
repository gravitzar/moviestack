package com.zerocoders.moviestack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zerocoders.moviestack.ui.theme.MovieStackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieStackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieStackTheme {
                MovieStackApp()
            }
        }
    }
}