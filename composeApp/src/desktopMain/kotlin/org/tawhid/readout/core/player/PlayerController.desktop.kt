package org.tawhid.readout.core.player

import org.tawhid.readout.core.player.domain.PlayerRepository
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter

import java.io.File
import java.nio.file.Files
import java.util.Base64

actual class PlayerController : PlayerRepository {

    private val mediaPlayerFactory: MediaPlayerFactory = MediaPlayerFactory()
    private val mediaPlayer: MediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer()

    override fun play(audioUrl: String) {
        stop()
        mediaPlayer.media().play(audioUrl)
    }

    override fun playAudioBase64(audioBase64: String) {
        stop()

        try {
            val decodedBytes = Base64.getDecoder().decode(audioBase64)
            val tempFile = File.createTempFile("audio", ".mp3")
            Files.write(tempFile.toPath(), decodedBytes)

            println("Play Clickkkk")

            mediaPlayer.media().play(tempFile.absolutePath)

            mediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
                override fun finished(mediaPlayer: MediaPlayer) {
                    tempFile.delete()
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun playAll(audioUrls: List<String>) {
        if (audioUrls.isEmpty()) return

        play(audioUrls[0])

        mediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            var index = 1

            override fun finished(mediaPlayer: MediaPlayer) {
                if (index < audioUrls.size) {
                    play(audioUrls[index])
                    index++
                }
            }
        })
    }

    override fun forward() {
        if (mediaPlayer.status().isPlaying) {
            val currentTime = mediaPlayer.status().time()
            val forwardTime = currentTime + 10000
            val duration = mediaPlayer.media().info().duration()
            mediaPlayer.controls().setTime(minOf(forwardTime, duration))
        }
    }

    override fun rewind() {
        if (mediaPlayer.status().isPlaying) {
            val currentTime = mediaPlayer.status().time()
            val rewindTime = currentTime - 10000
            mediaPlayer.controls().setTime(maxOf(rewindTime, 0))
        }
    }

    override fun pauseResume() {
        if (mediaPlayer.status().isPlaying) {
            mediaPlayer.controls().pause()
        } else {
            mediaPlayer.controls().play()
        }
    }

    fun stop() {
        if (mediaPlayer.status().isPlaying) {
            mediaPlayer.controls().stop()
        }
    }

    fun release() {
        mediaPlayer.release()
        mediaPlayerFactory.release()
    }
}