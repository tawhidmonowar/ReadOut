package org.tawhid.readout.core.player

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import org.tawhid.readout.core.player.domain.PlayerRepository
import java.io.File
import java.util.Base64

actual class PlayerController : PlayerRepository {

    private var mediaPlayer: MediaPlayer? = null

    override fun play(audioUrl: String) {
        mediaPlayer?.stop()
        val media = Media(audioUrl)
        mediaPlayer = MediaPlayer(media).apply {
            play()
        }
    }

    override fun playAudioBase64(audioBase64: String) {
        mediaPlayer?.stop()

        val decodedBytes = Base64.getDecoder().decode(audioBase64)
        val tempFile = File.createTempFile("audio", ".mp3")
        tempFile.writeBytes(decodedBytes)

        println("Play Clickkkk")

        val media = Media(tempFile.toURI().toString())
        mediaPlayer = MediaPlayer(media).apply {
            play()
        }

        mediaPlayer?.setOnEndOfMedia {
            tempFile.delete()
        }
    }

    override fun playAll(audioUrls: List<String>) {
        if (audioUrls.isEmpty()) return
        play(audioUrls.first())
        var index = 1
        mediaPlayer?.setOnEndOfMedia {
            if (index < audioUrls.size) {
                play(audioUrls[index])
                index++
            }
        }
    }

    override fun forward() {
        mediaPlayer?.let {
            val currentTime = it.currentTime.toMillis()
            val forwardTime = currentTime + 10000.0
            val duration = it.totalDuration.toMillis()
            it.seek(Duration.millis(minOf(forwardTime, duration)))
        }
    }

    override fun rewind() {
        mediaPlayer?.let {
            val currentTime = it.currentTime.toMillis()
            val rewindTime = currentTime - 10000.0
            it.seek(Duration.millis(maxOf(rewindTime, 0.0)))
        }
    }

    override fun pauseResume() {
        mediaPlayer?.let {
            if (it.status == MediaPlayer.Status.PLAYING) {
                it.pause()
            } else if (it.status == MediaPlayer.Status.PAUSED || it.status == MediaPlayer.Status.STOPPED) {
                it.play()
            }
        }
    }

    fun release() {
        mediaPlayer?.dispose()
    }
}
