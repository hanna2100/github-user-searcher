package com.example.githubusersearch.framework.presentation.userdetail.composable

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubusersearch.R
import com.example.githubusersearch.framework.presentation.theme.NanumSquareFamily
import kotlinx.coroutines.launch

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollapsableToolbar(
    textColorOverAvatar: Color,
    onBackButtonClick: () -> Unit,
) {

    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
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

        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
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
            val collapsedContentHeight = 70.dp

            Column {
                MotionComposeHeader(
                    progress = if (swipingState.progress.to == SwipingStates.COLLAPSED) {
                        swipingState.progress.fraction
                    } else {
                        1 - swipingState.progress.fraction
                    },
                    collapsedContentHeight = collapsedContentHeight,
                    textColorOverAvatar = textColorOverAvatar,
                    onBackButtonClick = onBackButtonClick
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
    textColorOverAvatar: Color,
    onBackButtonClick: () -> Unit,
    scrollableBody: @Composable (BoxScope)->Unit
) {

    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(collapsedContentHeight),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .layoutId("userDetailBox")
                .fillMaxWidth()
                .height(355.dp)
                .background(MaterialTheme.colors.primary)
                .alpha(1f - progress)
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp * (1 - progress) + (collapsedContentHeight * progress)))

            BioText("Hello world")
            IconText(R.drawable.ic_link, "https://github.com/blog")
            IconText(R.drawable.ic_location_city, "San Francisco")
            IconText(R.drawable.ic_celebration, "2008-01-14T04:33:35Z")
            IconFollowerFollowingText(20, 1)
        }

        val imageUrl = "https://img2.sbs.co.kr/img/sbs_cms/PG/2019/08/07/PG32010102_w640_h360.jpg"
        val request = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()

        AsyncImage(
            model = request,
            placeholder = painterResource(id = R.drawable.profile_img),
            contentDescription = "프로필 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("userAvatar")
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape((progress * 6).dp)),
        )

        val useLoginTextSize = ((2f - progress) * 10 + 10).sp
        val animatedTextColor = animateColorAsState(
            targetValue = if (progress == 1f) {
                MaterialTheme.colors.onPrimary
            } else {
                textColorOverAvatar
            }
        )

        Text(
            text = "유저 아이디",
            modifier = Modifier
                .layoutId("userLogin"),
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontFamily = NanumSquareFamily,
                fontSize = useLoginTextSize,
                color = animatedTextColor.value
            )
        )
        Text(
            text = "유저 이름",
            modifier = Modifier
                .layoutId("userName")
                .alpha(1 - progress)
            ,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Medium,
                color = animatedTextColor.value
            )
        )

        IconButton(
            modifier = Modifier
                .layoutId("backButton"),
            onClick = {
                onBackButtonClick()
            }
        ) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = animatedTextColor.value,
                modifier = Modifier.size(48.dp)
            )
        }

        Box(Modifier.layoutId("content")) {
            scrollableBody(this)
        }
    }
}

@Composable
fun BioText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary.copy(red = 0.2f, green = 0.21f, blue = 0.23f))
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun IconText(@DrawableRes res: Int, text: String) {
    Row(
        modifier = Modifier.padding(10.dp, 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = res),
            contentDescription = text,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onPrimary
            )
        )
    }
}

@Composable
fun IconFollowerFollowingText(follower: Int, following: Int) {
    Row(
        modifier = Modifier.padding(10.dp, 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "팔로우 팔로워",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = follower.toString(),
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onPrimary
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Followers",
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = following.toString(),
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onPrimary
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Followings",
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
        )
    }
}

private fun startConstraintSet() = ConstraintSet {
    val userDetailBox = createRefFor("userDetailBox")
    val userAvatar = createRefFor("userAvatar")
    val content = createRefFor("content")
    val userName = createRefFor("userName")
    val userLogin = createRefFor("userLogin")

    constrain(userDetailBox) {
        width = Dimension.fillToConstraints
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(userAvatar) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }

    constrain(userLogin) {
        start.linkTo(parent.start, 20.dp)
        bottom.linkTo(userAvatar.bottom, 40.dp)
    }

    constrain(userName) {
        start.linkTo(userLogin.start)
        top.linkTo(userLogin.bottom)
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
    val backButton = createRefFor("backButton")
    val userName = createRefFor("userName")
    val userLogin = createRefFor("userLogin")

    constrain(userDetailBox) {
        width = Dimension.fillToConstraints
        height = Dimension.value(collapsedContentHeight)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(userAvatar) {
        width = Dimension.value(60.dp)
        height = Dimension.value(60.dp)
        start.linkTo(parent.start, 56.dp)
        top.linkTo(parent.top)
        bottom.linkTo(userDetailBox.bottom)
    }

    constrain(userLogin) {
        start.linkTo(userAvatar.end, 12.dp)
        top.linkTo(userAvatar.top)
        bottom.linkTo(userAvatar.bottom)
    }

    constrain(userName) {
        start.linkTo(userLogin.start)
        top.linkTo(userLogin.bottom)
    }

    constrain(backButton) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
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