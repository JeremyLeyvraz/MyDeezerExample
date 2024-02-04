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

    var currentMusicName = mutableStateOf("")

    val name = playlist.name
    val image = playlist.image

    val dataResponseReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == PlayerService.ACTION_CURRENT_MUSIC_RESULT) {
                val responseData = intent.getStringExtra("musicId")
                currentMusicName.value = responseData!!
            }
        }
    }

    fun getMusics(): List<Music> {
        return playlist.getPlayList()
    }

    @SuppressLint("StaticFieldLeak")
    var context: Context? = null

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun init(context: Context) {
        this.context = context
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ACTION_CURRENT_MUSIC_RESULT))
    }

    fun play(musicId: String) {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.ACTION_PLAY
        playIntent.putExtra("musicId", musicId)
        context?.startService(playIntent)
    }

    fun pause() {
        val pauseIntent = Intent(context, PlayerService::class.java)
        pauseIntent.action = PlayerService.ACTION_PAUSE
        context?.startService(pauseIntent)
    }

    fun stop() {
        val stopIntent = Intent(context, PlayerService::class.java)
        stopIntent.action = PlayerService.ACTION_STOP
        context?.startService(stopIntent)
    }

}