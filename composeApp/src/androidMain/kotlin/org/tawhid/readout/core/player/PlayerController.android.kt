package org.tawhid.readout.core.player

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import org.tawhid.readout.core.player.domain.PlayerRepository

actual class PlayerController(context: Context) : PlayerRepository {

    private val player = ExoPlayer.Builder(context).build()

    override fun play(audioUrl: String) {
        player.clearMediaItems()
        val mediaItem = MediaItem.fromUri(audioUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

        player.play()
    }

    override fun playAll(audioUrls: List<String>) {
        player.clearMediaItems()
        audioUrls.forEach { url ->
            val mediaItem = MediaItem.fromUri(url)
            player.addMediaItem(mediaItem)
        }

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Log.e("PlayerController", "Player error: ${error.message}")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_BUFFERING -> Log.d("PlayerController", "Buffering...")
                    Player.STATE_READY -> Log.d("PlayerController", "Playback ready")
                    Player.STATE_ENDED -> Log.d("PlayerController", "Playback ended")
                    Player.STATE_IDLE -> Log.d("PlayerController", "Player idle")
                }
            }
        })

        player.prepare()
        player.play()
    }

    override fun forward() {
        val currentPosition = player.currentPosition
        val duration = player.duration
        val forwardPosition = currentPosition + 10000L
        if (forwardPosition <= duration) {
            player.seekTo(forwardPosition)
        } else {
            player.seekTo(duration)
        }
    }

    override fun rewind() {
        val currentPosition = player.currentPosition
        val rewindPosition = currentPosition - 10000L
        if (rewindPosition >= 0) {
            player.seekTo(rewindPosition)
        } else {
            player.seekTo(0)
        }
    }

    override fun pauseResume() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun release() {
        player.release()
    }
}