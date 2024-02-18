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

/**
 * ViewModel responsible for managing the playlist and playback state.
 */
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

    /**
     * BroadcastReceiver to receive updates from the PlayerService.
     */
    val dataResponseReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action) {
                PlayerService.STATE_PLAY -> {
                    val playIntent = Intent(context, PlayerService::class.java)
                    playIntent.action = PlayerService.REQUEST_METADATA
                    context?.startService(playIntent)
                }
                PlayerService.ANSWER_METADATA -> {
                    val isPlayingExtra = intent.getBooleanExtra("isPlaying", false)
                    val musicIdExtra = intent.getStringExtra("musicId")
                    val durationExtra = intent.getLongExtra("duration", 0L)
                    val positionExtra = intent.getLongExtra("currentPosition", 0L)

                    currentMusicName.value = musicIdExtra!!
                    currentMusic.value = getMusics().firstOrNull { it.name == currentMusicName.value }
                    isPlaying.value = currentMusic.value != null
                    isPause.value = !isPlayingExtra
                    duration.value = durationExtra
                    currentPosition.value = positionExtra
                    updateProgress()
                }
                PlayerService.UPDATE_PROGRESS -> {
                    val positionExtra = intent.getLongExtra("currentPosition", 0L)
                    val durationExtra = intent.getLongExtra("duration", 0L)
                    duration.value = durationExtra
                    currentPosition.value = positionExtra
                    updateProgress()
                }
            }
        }
    }

    /**
     * Updates the playback progress based on the current position and duration.
     */
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

    /**
     * Retrieves the list of music in the playlist.
     */
    fun getMusics(): List<Music> {
        return playlist.getPlayList()
    }

    /**
     * Initializes the ViewModel with the given context.
     */
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun init(context: Context) {
        this.context = context
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.STATE_PLAY))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.STATE_PAUSE))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.STATE_REPRISE))

        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.ANSWER_METADATA))
        context.registerReceiver(dataResponseReceiver, IntentFilter(PlayerService.UPDATE_PROGRESS))

        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.REQUEST_METADATA
        context.startService(playIntent)
    }

    /**
     * Plays the music with the given ID.
     */
    fun play(musicId: String) {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.REQUEST_PLAY
        playIntent.putExtra("musicId", musicId)
        context?.startService(playIntent)
    }

    /**
     * Pauses the current playback.
     */
    fun pause() {
        val pauseIntent = Intent(context, PlayerService::class.java)
        pauseIntent.action = PlayerService.REQUEST_PAUSE
        context?.startService(pauseIntent)
        isPause.value = !isPause.value
    }

    /**
     * Skips to the next track in the playlist.
     */
    fun next() {
        val nextIntent = Intent(context, PlayerService::class.java)
        nextIntent.action = PlayerService.REQUEST_NEXT
        context?.startService(nextIntent)
    }

    /**
     * Skips to the previous track in the playlist.
     */
    fun previous() {
        val nextIntent = Intent(context, PlayerService::class.java)
        nextIntent.action = PlayerService.REQUEST_PREVIOUS
        context?.startService(nextIntent)
    }

    /**
     * Seeks to a specific progress value in the current playback.
     */
    fun goTo(value: Float) {
        val playIntent = Intent(context, PlayerService::class.java)
        playIntent.action = PlayerService.REQUEST_GOTO
        playIntent.putExtra("progress", value)
        context?.startService(playIntent)
    }
}
