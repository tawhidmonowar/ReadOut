package org.tawhid.readout.core.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tawhid.readout.core.player.domain.PlayerRepository
import uk.co.caprica.vlcj.media.Media
import uk.co.caprica.vlcj.media.MediaEventAdapter
import uk.co.caprica.vlcj.media.Meta
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

actual class PlayerController : PlayerRepository {

    private val player = AudioPlayerComponent()
    private val options = arrayOf(
        ":network-caching=1000",
        ":https-user-agent=Mozilla/5.0"
    )

    override fun play(audioUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            player.mediaPlayer().media().startPaused(audioUrl, *options)
            player.mediaPlayer().media().parsing().parse()
            withContext(Dispatchers.Main) {
                player.mediaPlayer().controls().play()
                player.mediaPlayer().events().addMediaEventListener(object : MediaEventAdapter() {
                    override fun mediaMetaChanged(media: Media?, metaType: Meta?) {
                        val nowPlaying =
                            player.mediaPlayer().media().meta().get(Meta.NOW_PLAYING) ?: "Unknown"
                        println("Now Playing: $nowPlaying")
                        val title = player.mediaPlayer().media().meta().get(Meta.TITLE) ?: "Unknown"
                        val genre = player.mediaPlayer().media().meta().get(Meta.GENRE) ?: "Unknown"
                    }
                })
            }
        }
    }

    override fun pauseResume() {
        if (player.mediaPlayer().status().isPlaying) {
            player.mediaPlayer().controls().pause()
        } else {
            player.mediaPlayer().controls().play()
        }
    }

}