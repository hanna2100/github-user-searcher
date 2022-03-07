package com.example.githubusersearch.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun loadImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes @RawRes placeholderResource: Int?,
    contentDescription: String? = null,
) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build()

    AsyncImage(
        model = request,
        placeholder = if (placeholderResource == null) null else painterResource(placeholderResource),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}