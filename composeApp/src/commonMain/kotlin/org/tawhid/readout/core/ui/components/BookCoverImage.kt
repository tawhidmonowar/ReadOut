package org.tawhid.readout.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.ui.animation.PulseAnimation
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.book_cover
import readout.composeapp.generated.resources.book_cover_error_img

@Composable
fun BookCoverImage(
    imgUrl: String,
    height: Dp = 250.dp,
    aspectRatio: Float = 2 / 3f,
    errorImg: DrawableResource = Res.drawable.book_cover_error_img
) {
    var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }

    val painter = rememberAsyncImagePainter(
        model = imgUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        }
    )

    Box(
        modifier = Modifier
            .height(height)
            .aspectRatio(aspectRatio, matchHeightConstraintsFirst = true),
        contentAlignment = Alignment.TopCenter
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxSize(),
            shape = Shapes.small,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = small)
        ) {
            AnimatedContent(targetState = imageLoadResult) { result ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(extraSmall)
                        .clip(Shapes.extraSmall),
                    contentAlignment = Alignment.Center
                ) {
                    when (result) {
                        null -> PulseAnimation(modifier = Modifier.size(100.dp))
                        else -> Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(errorImg)
                            },
                            contentDescription = stringResource(Res.string.book_cover),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}