package org.tawhid.readout.core.player.domain

interface PlayerRepository {
    fun play(audioUrl : String)
    fun pauseResume()
}