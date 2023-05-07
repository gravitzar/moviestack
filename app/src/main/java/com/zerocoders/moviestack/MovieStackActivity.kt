package com.zerocoders.moviestack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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