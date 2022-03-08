package com.example.githubusersearch.framework.presentation.userdetail.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.common.composable.CircularIndicator
import com.example.githubusersearch.common.composable.LoadingShimmer
import com.example.githubusersearch.common.composable.loadImage
import com.example.githubusersearch.common.extensions.toDevLanguage
import com.example.githubusersearch.common.extensions.toSimpleFormat
import com.example.githubusersearch.common.extensions.topRectBorder
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.siddroid.holi.colors.MaterialColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RepositoryViewPager(
    firstPage: @Composable ()->Unit,
    secondPage: @Composable ()->Unit,
    pagerState: PagerState
) {
    HorizontalPager(
        count = 2,
        userScrollEnabled = pagerState.currentPage != 0,
        state = pagerState
    ) { page ->
        if (page == 0) {
            firstPage()
        } else {
            secondPage()
        }
    }
}


