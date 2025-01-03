package org.tawhid.readout.core.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.bookCoverAspectRatio
import org.tawhid.readout.core.theme.extraThin
import org.tawhid.readout.core.theme.horizontalGridMaxHeight
import org.tawhid.readout.core.theme.horizontalGridMaxWidth
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.shimmerColors
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero

object ShimmerEffect {
    @Composable
    fun ShimmerBrush(): Brush {
        val transition = rememberInfiniteTransition()
        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 400f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
                RepeatMode.Reverse
            ),
        )
        return remember {
            derivedStateOf {
                Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(translateAnimation, translateAnimation),
                    end = Offset(translateAnimation + 100f, translateAnimation + 100f),
                    tileMode = TileMode.Mirror,
                )
            }
        }.value
    }


    @Composable
    fun BookHorizontalGridItemShimmerEffect() {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.heightIn(max = horizontalGridMaxHeight),
            verticalArrangement = Arrangement.spacedBy(zero),
            horizontalArrangement = Arrangement.spacedBy(zero)
        ) {
            items(20) {
                BookGridItemShimmer()
            }
        }
    }

    @Composable
    fun BookGridItemShimmer() {
        val brush = ShimmerBrush()
        Surface(
            modifier = Modifier
                .width(horizontalGridMaxWidth)
                .padding(thin)
                .clip(Shapes.small)
                .then(Modifier.fillMaxWidth()),
            tonalElevation = thin,
            shape = Shapes.small,
        ) {
            Column(
                modifier = Modifier
                    .padding(small)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(bookCoverAspectRatio)
                        .clip(Shapes.small)
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(small))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(medium)
                        .clip(Shapes.extraSmall)
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(small))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .height(medium)
                        .clip(Shapes.extraSmall)
                        .background(brush)
                )
            }
        }
    }
}