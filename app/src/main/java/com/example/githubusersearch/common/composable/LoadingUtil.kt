package com.example.githubusersearch.common.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.siddroid.holi.colors.MaterialColor


@Composable
fun LoadingShimmer(
    width: Dp,
    height: Dp,
    padding: Dp = 0.dp,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.width(width).height(height).padding(padding)
    ) {
        val cardWidthPx = with(LocalDensity.current) { ( width - (padding * 2)).toPx() }
        val cardHeightPx = with(LocalDensity.current) { (height - padding).toPx() }
        val gradientWidth: Float = (0.1f * cardWidthPx)

        val infiniteTransition = rememberInfiniteTransition()
        val xCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWidthPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )
        val yCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val colors = listOf(
            Color.LightGray.copy(red = .85f, green = .85f, blue = .85f),
            Color.LightGray.copy(red = .99f, green = .99f, blue = .99f),
            Color.LightGray.copy(red = .85f, green = .85f, blue = .85f),
        )
        LazyColumn {
            item {
                val brush = Brush.linearGradient(
                    colors,
                    start = Offset(xCardShimmer.value - gradientWidth, yCardShimmer.value - gradientWidth),
                    end = Offset(xCardShimmer.value, yCardShimmer.value)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .background(brush = brush)
                )
            }
        }
    }

}

@Composable
fun CircularIndicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(35.dp),
            color = MaterialColor.GREY_200,
            strokeWidth = 4.dp
        )
    }
}

@Preview
@Composable
fun LoadingShimmerTest() {
    LoadingShimmer(
        width = 400.dp,
        height = 200.dp,
        padding = 10.dp
    )
}