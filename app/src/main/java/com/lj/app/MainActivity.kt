package com.lj.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import com.lj.app.ui.theme.TemplateTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity: define the navigation and the theme.
 *
 * Ex: https://www.droidcon.com/2022/10/03/adaptive-ui-with-jetpack-compose/
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                MyApp(windowSizeClass = windowSizeClass)
            }
        }
    }
}

@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactUI()
        }
        else -> {
            ExpandedUI()
        }
    }
}

@Composable
fun CompactUI() {
    Text(text = "Compact")
}

@Composable
fun ExpandedUI() {
    Text(text = "Expanded")
}