package com.lj.app.manager

import android.content.Context
import android.content.Intent
import com.lj.app.service.PlayerService

class MusicManager {

    lateinit var context: Context

    var playlistId: String = ""
    var musicId: String = ""

    fun init(context: Context) {
        this.context = context
    }

    fun play() {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.ACTION_PLAY
        // Ajoutez des données supplémentaires à l'intention si nécessaire
        // playIntent.putExtra("key", "value")

        // Envoyez l'intention au service
        context.startService(playIntent)
    }

    fun pause() {
        val pauseIntent = Intent(context, PlayerService::class.java)
        pauseIntent.action = PlayerService.ACTION_PAUSE
        // Ajoutez des données supplémentaires à l'intention si nécessaire
        // playIntent.putExtra("key", "value")

        // Envoyez l'intention au service
        context.startService(pauseIntent)
    }

    fun stop() {
        val stopIntent = Intent(context, PlayerService::class.java)
        stopIntent.action = PlayerService.ACTION_STOP
        // Ajoutez des données supplémentaires à l'intention si nécessaire
        // playIntent.putExtra("key", "value")

        // Envoyez l'intention au service
        context.startService(stopIntent)
    }

}