package com.example.githubusersearch.framework.presentation.search.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.User

@Composable
fun UserListView(
    users: List<User>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
    ) {
        itemsIndexed(items = users, key = {index, item -> item.id }) { index, item ->
            UserCard(
                imageUrl = item.avatarUrl,
                login = item.login
            )
        }
    }
}

@Composable
fun UserCard(
    imageUrl: String,
    login: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .clickable { }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.github_icon),
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