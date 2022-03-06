package com.example.githubusersearch.framework.presentation.userdetail.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import com.example.githubusersearch.R

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollapsableToolbar() {

    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        val connection = remember {
            object : NestedScrollConnection {

                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    return if (delta < 0) {
                        swipingState.performDrag(delta).toOffset()
                    } else {
                        Offset.Zero
                    }
                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    return swipingState.performDrag(delta).toOffset()

                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    swipingState.performFling(velocity = available.y)
                    return super.onPostFling(consumed, available)
                }

                private fun Float.toOffset() = Offset(0f, this)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) }, // 각 앵커로 스냅되는 기준(진행도)를 정의
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,
                        heightInPx to SwipingStates.EXPANDED
                    )
                )
                .nestedScroll(connection)
        ) {
            val collapsedContentHeight = 60.dp
            Column {
                MotionComposeHeader(
                    progress = if (swipingState.progress.to == SwipingStates.COLLAPSED) {
                        swipingState.progress.fraction
                    } else {
                        1 - swipingState.progress.fraction
                    },
                    collapsedContentHeight = collapsedContentHeight
                ) {
                    ScrollableContent(collapsedContentHeight)
                }
            }

        }
    }
}

@Composable
fun MotionComposeHeader(
    progress: Float,
    collapsedContentHeight: Dp,
    scrollableBody: @Composable (BoxScope)->Unit
) {
    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(collapsedContentHeight),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .layoutId("userDetailBox")
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Yellow)
                .alpha(1f - progress)
        ) {

        }
        Image(
            painter = painterResource(id = R.drawable.github_icon),
            contentDescription = "유저 아바타",
            modifier = Modifier
                .layoutId("userAvatar")
                .size(100.dp),
        )
        Box(Modifier.layoutId("content")) {
            scrollableBody(this)
        }
    }
}

private fun startConstraintSet() = ConstraintSet {
    val userDetailBox = createRefFor("userDetailBox")
    val userAvatar = createRefFor("userAvatar")
    val content = createRefFor("content")

    constrain(userDetailBox) {
        width = Dimension.fillToConstraints
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(userAvatar) {
        start.linkTo(parent.start, 16.dp)
        top.linkTo(parent.top, 16.dp)
    }

    constrain(content) {
        width = Dimension.fillToConstraints
        top.linkTo(userDetailBox.bottom, 8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

private fun endConstraintSet(collapsedContentHeight: Dp) = ConstraintSet {
    val userDetailBox = createRefFor("userDetailBox")
    val userAvatar = createRefFor("userAvatar")
    val content = createRefFor("content")

    constrain(userDetailBox) {
        width = Dimension.fillToConstraints
        height = Dimension.value(collapsedContentHeight)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(userAvatar) {
        start.linkTo(parent.start)
        top.linkTo(parent.top, 8.dp)
        end.linkTo(parent.end)
        bottom.linkTo(userDetailBox.bottom)
    }

    constrain(content) {
        width = Dimension.fillToConstraints
        top.linkTo(userDetailBox.bottom, 8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

@Composable
internal fun ScrollableContent(collapsedContentHeight: Dp) {
    val list = listOf(1..20).flatten()
    LazyColumn(
        Modifier.padding(
            bottom = collapsedContentHeight
        )
    ) {
        items(
            items = list,
            itemContent = { id ->
                ScrollableContentItem(id = id.toString())
            },
        )
    }
}

@Composable
private fun ScrollableContentItem(id: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(16.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "Item $id",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h5
            )
        }

    }
}