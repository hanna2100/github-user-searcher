package com.example.githubusersearch.framework.presentation.userdetail.composable

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.common.composable.LoadingShimmer
import com.example.githubusersearch.common.composable.loadImage
import com.example.githubusersearch.common.extensions.toSince
import com.example.githubusersearch.framework.presentation.theme.NanumSquareFamily
import com.google.accompanist.pager.PagerState

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun CollapsableToolbar(
    textColorOverAvatar: Color,
    user: User,
    isLoadingUser: Boolean,
    onBackButtonClick: () -> Unit,
    repositories: List<Repository>,
    isLoadingRepositories: Boolean,
    onRepositoryClick: (owner: String, repo: String) -> Unit,
    repositoryDetail: Repository,
    isLoadingRepositoryDetail: Boolean,
    repositoryViewPagerState: PagerState,
    isLoadingReadMeMarkdown: Boolean
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
                    user = user,
                    isLoadingUser = isLoadingUser,
                    progress = if (swipingState.progress.to == SwipingStates.COLLAPSED) {
                        swipingState.progress.fraction
                    } else {
                        1 - swipingState.progress.fraction
                    },
                    collapsedContentHeight = collapsedContentHeight,
                    textColorOverAvatar = textColorOverAvatar,
                    onBackButtonClick = onBackButtonClick
                ) {
                    RepositoryViewPager(
                        pagerState = repositoryViewPagerState,
                        firstPage = {
                            RepositoryListView(
                                collapsedContentHeight,
                                repositories,
                                isLoadingRepositories,
                                onRepositoryClick,
                            )
                        },
                        secondPage = {
                            RepositoryDetailView(
                                collapsedContentHeight = collapsedContentHeight,
                                repository = repositoryDetail,
                                isLoadingRepositoryDetail = isLoadingRepositoryDetail,
                                isLoadingReadMeMarkdown = isLoadingReadMeMarkdown
                            )
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun MotionComposeHeader(
    user: User,
    isLoadingUser: Boolean,
    progress: Float,
    collapsedContentHeight: Dp,
    textColorOverAvatar: Color,
    onBackButtonClick: () -> Unit,
    scrollableBody: @Composable (BoxScope)->Unit
) {

    @Composable
    fun IconText(@DrawableRes @RawRes res: Int, text: String) {
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
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
    }

    @Composable
    fun BioText(text: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    MaterialTheme.colors.primary.copy(
                        red = 0.2f,
                        green = 0.21f,
                        blue = 0.23f
                    )
                )
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }


    @Composable
    fun IconFollowerFollowingText(follower: Int, following: Int) {
        Row(
            modifier = Modifier.padding(10.dp, 1.dp, 10.dp, 10.dp),
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
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Followers",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = following.toString(),
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Followings",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
                )
            )
        }
    }

    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(collapsedContentHeight),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        if (isLoadingUser) {
            Column(
                modifier = Modifier
                    .layoutId("userDetailBox")
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.primary)
                    .alpha(1f - progress)
            ) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp * (1 - progress) + (collapsedContentHeight * progress)))

                val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
                LoadingShimmer(
                    width = screenWidth,
                    height = 100.dp,
                    padding = 10.dp,
                    modifier = Modifier.alpha(.5f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .layoutId("userDetailBox")
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.primary)
                    .alpha(1f - progress)
            ) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp * (1 - progress) + (collapsedContentHeight * progress)))

                BioText(user.detailInfo?.bio?: "")
                IconText(R.drawable.ic_link, user.detailInfo?.blog?: "")
                IconText(R.drawable.ic_location_city, user.detailInfo?.location?: "")
                IconText(R.drawable.ic_celebration, user.detailInfo?.createAt?.toSince()?: "")
                IconFollowerFollowingText(
                    user.detailInfo?.followers?: 0,
                    user.detailInfo?.following?: 0
                )
            }
        }

        if (isLoadingUser) {
            val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
            LoadingShimmer(
                width = screenWidth,
                height = 200.dp,
                modifier = Modifier
                    .layoutId("userAvatar")
                    .clip(RoundedCornerShape((progress * 6).dp))
            )
        } else {
            loadImage(
                imageUrl = user.defaultInfo.avatarUrl,
                contentDescription = "프로필 이미지",
                placeholderResource = R.drawable.profile_img,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .layoutId("userAvatar")
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape((progress * 6).dp))
            )
        }

        val useLoginTextSize = ((2f - progress) * 10 + 10).sp
        val animatedTextColor = animateColorAsState(
            targetValue = if (progress == 1f) {
                MaterialTheme.colors.onPrimary
            } else {
                textColorOverAvatar
            }
        )

        Text(
            text = user.defaultInfo.login,
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
            text = user.detailInfo?.name?: "",
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

        Box(
            Modifier.layoutId("content")
        ) {
            scrollableBody(this)
        }
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