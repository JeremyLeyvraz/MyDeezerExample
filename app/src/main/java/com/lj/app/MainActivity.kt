package com.lj.app

import android.Manifest
import android.content.Intent
import androidx.compose.material3.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.lj.app.navigation.MainNavigation
import com.lj.app.service.PlayerService
import com.lj.app.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity: define the navigation and the theme.
 *
 * Ex: https://www.droidcon.com/2022/10/03/adaptive-ui-with-jetpack-compose/
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()
    private var isServiceRunning = false

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            viewModel.mContext = LocalContext.current

            // A surface container using the 'background' color from the theme
            val permissionState = rememberPermissionState(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        permissionState.launchPermissionRequest()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            val windowSizeClass = calculateWindowSizeClass(this)
            MyApp(windowSizeClass = windowSizeClass)

            startService()
        }
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, PlayerService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}

@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        MainNavigation(windowSizeClass)
    }
}
