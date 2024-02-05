package com.lj.app

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.material3.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.offline.DownloadService.startForeground
import com.lj.app.navigation.MainNavigation
import com.lj.app.service.PlayerService
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                123
            )
            //return
        }
        notificationManager.notify(notificationId, notification)

        val serviceIntent = Intent(this, PlayerService::class.java)
        startService(serviceIntent)

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
    MainNavigation(windowSizeClass)
}
