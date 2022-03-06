package com.example.githubusersearch.framework.presentation.search.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.common.extensions.OnBottomReached
import com.example.githubusersearch.framework.presentation.theme.DarkRippleTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListView(
    users: List<User>,
    loading: Boolean,
    onUserClick: (String) -> Unit,
    onBottomReached: ()->Unit
) {
    val listState = rememberLazyListState()
    CompositionLocalProvider(LocalRippleTheme provides DarkRippleTheme) {
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                state = listState,
            ) {
                itemsIndexed(items = users) { index, item ->
                    UserCard(
                        imageUrl = item.defaultInfo.avatarUrl,
                        login = item.defaultInfo.login,
                        onUserClick = onUserClick,
                    )
                }
            }
            AnimatedVisibility(
                visible = loading,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 200,
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 200,
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DotsTyping(
                        dotColor = Color.White,
                        dotSize = 14.dp,
                        delayUnit = 300
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Loading",
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
    listState.OnBottomReached {
        onBottomReached()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(
    imageUrl: String,
    login: String,
    onUserClick: (String) -> Unit
) {
    val animatedProgress = remember { Animatable(initialValue = 200f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .graphicsLayer(translationX = animatedProgress.value),
        backgroundColor = MaterialTheme.colors.background,
        onClick = {
            onUserClick(login)
        }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val defaultImg = R.drawable.profile_img
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(defaultImg),
                contentDescription = "프로필 이미지",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = login,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun DotsTyping(
    dotColor: Color,
    dotSize: Dp,
    delayUnit: Int
) {
    val maxOffset = 10f

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = dotColor,
                shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        )
    )

    val offset1 by animateOffsetWithDelay(0)
    val offset2 by animateOffsetWithDelay(delayUnit)
    val offset3 by animateOffsetWithDelay(delayUnit * 2)


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 5.dp

        Dot(offset1)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3)
    }
}