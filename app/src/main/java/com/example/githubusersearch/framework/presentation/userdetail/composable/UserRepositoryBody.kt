package com.example.githubusersearch.framework.presentation.userdetail.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.common.composable.loadImage
import com.example.githubusersearch.common.extensions.toDevLanguage
import com.example.githubusersearch.common.extensions.topRectBorder
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme


@Composable
internal fun RepositoryListView (
    collapsedContentHeight: Dp,
    repositories: List<Repository>
) {
    LazyColumn(
        Modifier.padding(
            bottom = collapsedContentHeight
        )
    ) {
        itemsIndexed(repositories){ index, repository ->
            RepositoryCard(index, repository)
        }
    }
}

@Composable
private fun RepositoryCard(index: Int, repository: Repository) {
    println("index = $index")
    @Composable
    fun IconText(@DrawableRes icon: Int, iconColor: Color = Color.Unspecified, text: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text,
                modifier = Modifier.size(16.dp),
                tint = iconColor
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    GithubUserSearchTheme {
        val cardModifier = if(index == 0) {
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        } else {
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .topRectBorder(brush = SolidColor(Color.Gray))
        }
        Card(
            modifier = cardModifier,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 15.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    loadImage(
                        imageUrl = repository.ownerAvatarUrl,
                        placeholderResource = R.drawable.profile_img,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = repository.ownerLogin,
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.25.sp,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                        )
                    )
                }
                Spacer(Modifier.size(10.dp))
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = repository.fullName,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                )
                Spacer(Modifier.size(10.dp))
                Row {
                    IconText(
                        icon = R.drawable.ic_star,
                        text = repository.stargazersCount.toString()
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    IconText(
                        icon = R.drawable.ic_git_fork,
                        iconColor = Color.LightGray,
                        text = repository.forksCount.toString()
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    IconText(
                        icon = repository.language.icon,
                        text = repository.language.name
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun test() {
    val list = listOf(
        Repository(
            ownerLogin = "abcd",
            ownerAvatarUrl = "https://dimg.donga.com/wps/NEWS/IMAGE/2019/04/25/95215551.1.jpg",
            name = "dfaifjl",
            fullName = "adfafeafd adfaf",
            language = "C".toDevLanguage(),
            forksCount = 10,
            stargazersCount = 1
        ),
        Repository(
            ownerLogin = "abcd",
            ownerAvatarUrl = "https://dimg.donga.com/wps/NEWS/IMAGE/2019/04/25/95215551.1.jpg",
            name = "dfaifjl",
            fullName = "adfafeafd adfaf",
            language = "JavaScript".toDevLanguage(),
            forksCount = 10,
            stargazersCount = 1
        )
    )
    RepositoryListView(100.dp, list)

}

