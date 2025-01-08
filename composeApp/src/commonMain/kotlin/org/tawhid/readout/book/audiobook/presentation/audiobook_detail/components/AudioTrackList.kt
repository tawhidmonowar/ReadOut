package org.tawhid.readout.book.audiobook.presentation.audiobook_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tawhid.readout.book.audiobook.domain.AudioBookTracks
import org.tawhid.readout.core.theme.maxWidthIn
import org.tawhid.readout.core.theme.medium

fun LazyListScope.AudioTrackList(
    audioTracks: List<AudioBookTracks>,
    onPlayClick: (String) -> Unit,
) {
    items(
        items = audioTracks,
        key = { it.id }
    ) { audioTrack ->

        AudioTrackListItem(
            audioTrack = audioTrack,
            onPlayClick = { listenUrl ->
                onPlayClick(listenUrl)
            },
            modifier = Modifier
                .widthIn(max = maxWidthIn)
                .fillMaxWidth()
                .padding(horizontal = medium),
        )
    }
}