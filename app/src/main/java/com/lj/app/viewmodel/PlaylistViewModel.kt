package com.lj.app.viewmodel

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lj.app.model.Music
import com.lj.app.model.Playlist
import com.lj.app.service.PlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(): ViewModel() {

    private val playlist = Playlist()
    @SuppressLint("StaticFieldLeak")
    var context: Context? = null

    var currentMusic = mutableStateOf<Music?>(null)
    var currentMusicName = mutableStateOf("")

    var duration = mutableStateOf(0L)
    var currentPosition = mutableStateOf(0L)
    var progress = mutableStateOf(0f)

    var isPlaying = mutableStateOf(false)
    var isPause = mutableStateOf(false)

    val name = playlist.name
    val image = playlist.image

    val dataResponseReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == PlayerService.ACTION_CURRENT_MUSIC_RESULT) {
                val responseData = intent.getStringExtra("musicId")
                currentMusicName.value = responseData!!
                currentMusic.value = getMusics().firstOrNull { it.name == currentMusicName.value }
            }
            if (intent?.action == PlayerService.ACTION_DURATION) {
                val responseData = intent.getLongExtra("duration", 0L)
                duration.value = responseData
            }
            if (intent?.action == PlayerService.ACTION_CURRENT_POSITION) {
                val responseData = intent.getLongExtra("currentPosition", 0L)
                currentPosition.value = responseData
                updateProgress()
            }
            if (intent?.action == PlayerService.ACTION_APPLICATION_RESUME_RESULT) {
                val isPlayingExtra = intent.getBooleanExtra("isPlaying", false)
                isPlaying.value = currentMusic.value != null
                isPause.value = !isPlayingExtra
            }
        }
    }

    private fun updateProgress() {
        duration.value.let { it ->

            if (it <= 0) {
                return
            }

            currentPosition.value.let { pos ->
                if (pos in 0..it) {
                    progress.value = (pos.toFloat() / it.toFloat()) * 100
                }

            }
        }
    }

    fun goTo(value: Float) {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.ACTION_GOTO
        playIntent.putExtra("progress", value)
        context?.startService(playIntent)
    }

    fun getMusics(): List<Music> {
        return playlist.getPlayList()
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun init(context: Context) {
        this.context = context
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ACTION_CURRENT_MUSIC_RESULT))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ACTION_DURATION))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ACTION_CURRENT_POSITION))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ACTION_APPLICATION_RESUME_RESULT))

        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.ACTION_APPLICATION_RESUME
        context.startService(playIntent)
    }

    fun play(musicId: String) {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.ACTION_PLAY
        playIntent.putExtra("musicId", musicId)
        context?.startService(playIntent)
        isPlaying.value = true
        isPause.value = false
    }

    fun pause() {
        val pauseIntent = Intent(context, PlayerService::class.java)
        pauseIntent.action = PlayerService.ACTION_PAUSE
        context?.startService(pauseIntent)
        isPause.value = !isPause.value
    }

    fun stop() {
        val stopIntent = Intent(context, PlayerService::class.java)
        stopIntent.action = PlayerService.ACTION_STOP
        context?.startService(stopIntent)
    }

    fun next() {
        val nextIntent = Intent(context, PlayerService::class.java)
        nextIntent.action = PlayerService.ACTION_NEXT
        context?.startService(nextIntent)
        isPlaying.value = true
        isPause.value = false
    }

    fun previous() {
        val nextIntent = Intent(context, PlayerService::class.java)
        nextIntent.action = PlayerService.ACTION_PREVIOUS
        context?.startService(nextIntent)
        isPlaying.value = true
        isPause.value = false
    }
}
