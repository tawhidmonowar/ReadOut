package org.tawhid.readout.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.tawhid.readout.core.ui.animation.PulseAnimation

@Composable
fun CustomAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = { PulseAnimation(modifier = Modifier.size(60.dp)) },
    errorImage: Painter,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var imgLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            imgLoadResult = if (
                it.painter.intrinsicSize.height > 1 &&
                it.painter.intrinsicSize.width > 1
            ) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid Image"))
            }
        },
        onError = {
           // it.result.throwable.printStackTrace()
            imgLoadResult = Result.failure(it.result.throwable)
        }
    )

    val transition by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 800)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val result = imgLoadResult) {
            null -> placeholder()
            else -> Image(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationX = (1f - transition) * 30f
                        val scale = 0.8f + (0.2f * transition)
                        scaleX = scale
                        scaleY = scale
                    },
                painter = if (result.isSuccess) painter else errorImage,
                contentDescription = contentDescription,
                contentScale = if (result.isSuccess) contentScale else ContentScale.Fit
            )
        }
    }
}