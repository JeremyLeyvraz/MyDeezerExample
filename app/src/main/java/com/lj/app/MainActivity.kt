package com.lj.app

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.material3.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lj.app.navigation.MainNavigation
import com.lj.app.service.PlayerService
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

        val notificationId = 1
        val notificationChannelId = "foreground_service_channel"
        val notificationIntent = Intent(this, PlayerService::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Foreground Service Example")
            .setContentText("message")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                123
            )
        }
        notificationManager.notify(notificationId, notification)

        val serviceIntent = Intent(this, PlayerService::class.java)
        startService(serviceIntent)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MyApp(windowSizeClass = windowSizeClass)
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
