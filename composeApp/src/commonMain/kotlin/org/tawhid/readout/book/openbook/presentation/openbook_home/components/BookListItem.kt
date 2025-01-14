package org.tawhid.readout.book.openbook.presentation.openbook_home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Star
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.theme.SandYellow
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.ui.animation.PulseAnimation
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.book_cover_error_img
import readout.composeapp.generated.resources.right_arrow
import kotlin.math.round

@Composable
fun BookListItem(
    book: Book,
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
                        it.result.throwable.printStackTrace()
                        imgLoadResult = Result.failure(it.result.throwable)
                    }
                )

                val painterState by painter.state.collectAsStateWithLifecycle()

                val transition by animateFloatAsState(
                    targetValue = if (painterState is AsyncImagePainter.State.Success) {
                        1f
                    } else {
                        0f
                    },
                    animationSpec = tween(durationMillis = 800)
                )


                when (val result = imgLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )

                    else -> {
                        Image(
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f,
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer {
                                    rotationX = (1f - transition) * 30f
                                    val scale = 0.8f + (0.2f * transition)
                                    scaleX = scale
                                    scaleY = scale
                                },
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.book_cover_error_img)
                            },
                            contentDescription = book.title,
                            contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
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
                        text = authorName,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                book.avgRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${round(rating * 10) / 10.0}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }
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