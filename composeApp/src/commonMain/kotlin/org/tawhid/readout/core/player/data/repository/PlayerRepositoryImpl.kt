package org.tawhid.readout.core.player.data.repository

import org.tawhid.readout.core.player.domain.PlayerRepository
import org.tawhid.readout.core.player.PlayerController

class PlayerRepositoryImpl(
    private val playerController: PlayerController
) : PlayerRepository {
    override fun play(audioUrl: String) {
        playerController.play(audioUrl)
    }

    override fun pauseResume() {
        playerController.pauseResume()
    }
}