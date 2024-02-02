package com.lj.app.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lj.app.composable.HelloViewModel
import com.lj.app.composable.HelloWorldCompactComposable
import com.lj.app.composable.HelloWorldExpandedComposable

private const val HOME_DESTINATION = "home"

/**
 * Define the navigation through the application.
 */
@Composable
fun MainNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_DESTINATION) {
        composable(HOME_DESTINATION)
        {
            val viewModel = hiltViewModel<HelloViewModel>()
            viewModel.init(LocalContext.current)
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    HelloWorldCompactComposable(windowSizeClass, viewModel)
                }
                else -> {
                    HelloWorldExpandedComposable(windowSizeClass, viewModel)
                }
            }
        }
    }
}
