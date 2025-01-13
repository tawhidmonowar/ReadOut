package org.tawhid.readout.book.audiobook.presentation.audiobook_home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.ui.animation.PulseAnimation
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.audiobook_cover_error_img
import readout.composeapp.generated.resources.right_arrow

@Composable
fun AudioBookListItem(
    book: AudioBook,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(targetValue = if (isHovered) 1.03f else 1f)

    Surface(
        modifier = modifier
            .padding(extraSmall)
            .clip(Shapes.small)
            .scale(scale)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                onClick = onBookClick
            ),
        tonalElevation = thin,
        shape = Shapes.small
    ) {
        Row(
            modifier = Modifier
                .padding(medium)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(medium)
        ) {
            Box(
                modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imgLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imgUrl,
                    onSuccess = {
                        imgLoadResult =
                            if (it.painter.intrinsicSize.height > 1 && it.painter.intrinsicSize.width > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image"))
                            }
                    },
                    onError = {
                        //it.result.throwable.printStackTrace()
                        imgLoadResult = Result.failure(it.result.throwable)
                    }
                )

                when (val result = imgLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(100.dp)
                    )

                    else -> {
                        Image(
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 1f,
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.audiobook_cover_error_img)
                            },
                            contentDescription = book.title,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = "By $authorName",
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                book.totalTime?.let { totalTime ->
                    Text(
                        text = "Total Time: $totalTime",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.right_arrow)
            )
        }
    }
}