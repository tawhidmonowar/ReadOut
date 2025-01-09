package org.tawhid.readout.core.player.domain

interface PlayerRepository {
    fun play(audioUrl: String)
    fun playAudioBase64(audioBase64: String)
    fun playAll(audioUrls : List<String>)
    fun forward()
    fun rewind()
    fun pauseResume()
}