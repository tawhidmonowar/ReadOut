package org.tawhid.readout.core.player

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import org.tawhid.readout.core.player.domain.PlayerRepository
import java.io.File

actual class PlayerController(context: Context) : PlayerRepository {

    private val player = ExoPlayer.Builder(context).build()
    private val tempDir = context.cacheDir

    override fun play(audioUrl: String) {
        player.clearMediaItems()
        val mediaItem = MediaItem.fromUri(audioUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun playAudioBase64(audioBase64: String) {
        val decodedBytes = Base64.decode(audioBase64, Base64.DEFAULT)

        val tempFile = File.createTempFile("audio", ".mp3", tempDir)
        tempFile.writeBytes(decodedBytes)

        Log.d("PlayerController", "Temp file created at: ${tempFile.absolutePath}")

        player.clearMediaItems()
        val mediaItem = MediaItem.fromUri(Uri.fromFile(tempFile))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    tempFile.delete()
                    Log.d("PlayerController", "Temp file deleted after playback.")
                }
            }
        })
    }

    override fun playAll(audioUrls: List<String>) {
        player.clearMediaItems()
        audioUrls.forEach { url ->
            val mediaItem = MediaItem.fromUri(url)
            player.addMediaItem(mediaItem)
        }
        player.prepare()
        player.play()
    }

    override fun forward() {
        val currentPosition = player.currentPosition
        val duration = player.duration
        val forwardPosition = currentPosition + 10000L
        player.seekTo(minOf(forwardPosition, duration))
    }

    override fun rewind() {
        val currentPosition = player.currentPosition
        player.seekTo(maxOf(currentPosition - 10000L, 0L))
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