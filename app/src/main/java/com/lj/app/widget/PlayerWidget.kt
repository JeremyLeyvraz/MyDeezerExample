package com.lj.app.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.lj.app.MainActivity
import com.lj.app.R
import com.lj.app.viewmodel.PlaylistViewModel

class PlayerWidget : GlanceAppWidget() {


    private var viewModel = PlaylistViewModel()



    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            viewModel.init(context)
            // create your AppWidget here
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {

        val context = LocalContext.current

        Row(modifier = GlanceModifier.background(Color(55,0,255)).fillMaxWidth().height(80.dp),
            verticalAlignment = Alignment.CenterVertically) {
            viewModel.currentMusic.value?.let {
                Image(
                    provider = ImageProvider(it.cover),
                    contentDescription = LocalContext.current.resources.getString(R.string.app_name),
                    modifier = GlanceModifier.size(80.dp, 80.dp).clickable {
                        openApp(context)
                    },
                    contentScale = ContentScale.FillBounds
                )

                Column(modifier = GlanceModifier.defaultWeight().padding(4.dp)) {
                    Text(
                        text = it.name,
                        style = TextStyle(
                            fontWeight = androidx.glance.text.FontWeight.Medium,
                            color = ColorProvider(Color.Black),
                            fontSize = 16.sp,
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = it.artist,
                        style = TextStyle(
                            fontWeight = androidx.glance.text.FontWeight.Normal,
                            color = ColorProvider(Color.Black),
                            fontSize = 16.sp,
                        ),
                        maxLines = 1
                    )

                    Row(
                        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = GlanceModifier.defaultWeight())
                        Image(
                            provider = ImageProvider(R.drawable.btn_previous),
                            contentDescription = LocalContext.current.resources.getString(R.string.app_name),
                            modifier = GlanceModifier.size(32.dp, 32.dp).clickable {
                                viewModel.previous()
                            },
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = GlanceModifier.defaultWeight())
                        Image(
                            provider = ImageProvider(if (viewModel.isPause.value) R.drawable.btn_play else R.drawable.btn_pause),
                            contentDescription = LocalContext.current.resources.getString(R.string.app_name),
                            modifier = GlanceModifier.size(32.dp, 32.dp).clickable {
                                viewModel.pause()
                            },
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = GlanceModifier.defaultWeight())
                        Image(
                            provider = ImageProvider(R.drawable.btn_next),
                            contentDescription = LocalContext.current.resources.getString(R.string.app_name),
                            modifier = GlanceModifier.size(32.dp, 32.dp).clickable {
                                viewModel.next()
                            },
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = GlanceModifier.defaultWeight())
                    }
                }
            } ?: run {
                Text(text = "Open app")
            }
        }
    }

    private fun openApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

}