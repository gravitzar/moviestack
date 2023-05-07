@file:OptIn(ExperimentalMaterial3Api::class)

package com.zerocoders.moviestack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.zerocoders.moviestack.navigation.BottomNavigationItem
import com.zerocoders.moviestack.navigation.MovieStackNavHost

@Composable
fun MovieStackApp(
    appState: MovieStackAppState = rememberMovieStackAppState()
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
            ) {
                NavigationBar {
                    appState.bottomNavItems.forEach { menuItem: BottomNavigationItem ->
                        val selected = menuItem.route == appState.currentDestination?.route

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                appState.navController.navigate(menuItem.route) {
                                    popUpTo(appState.navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(
                                    text = menuItem.label,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = menuItem.icon,
                                    contentDescription = "${menuItem.name} Icon"
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            MovieStackNavHost(navController = appState.navController)
        }
    }
}