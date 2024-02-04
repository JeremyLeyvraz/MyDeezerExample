package com.lj.app.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.lj.app.service.PlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicViewModel  @Inject constructor(): ViewModel() {

    lateinit var context: Context

    lateinit var playlistId: String
    lateinit var musicId: String

    fun init(context: Context, playlistId: String, musicId: String) {
        this.context = context
        this.playlistId = playlistId
        this.musicId = musicId
    }

    fun play() {
        val playIntent = Intent(PlayerService.ACTION_PLAY)
        // Ajoutez des données supplémentaires à l'intention si nécessaire
        // playIntent.putExtra("key", "value")

        // Envoyez l'intention au service
        context.sendBroadcast(playIntent)
    }

    fun stop() {
        val playIntent = Intent(PlayerService.ACTION_STOP)
        // Ajoutez des données supplémentaires à l'intention si nécessaire
        // playIntent.putExtra("key", "value")

        // Envoyez l'intention au service
        context.sendBroadcast(playIntent)
    }

}