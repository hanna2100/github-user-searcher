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
import androidx.compose.ui.layout.ContentScale
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
import com.example.githubusersearch.common.composable.loadImage
import com.example.githubusersearch.common.extensions.toDevLanguage
import com.example.githubusersearch.common.extensions.toSimpleFormat
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositoryDetailView(
    collapsedContentHeight: Dp,
    repository: Repository,
    isLoadingRepositoryDetail: Boolean,
) {

    @Composable
    fun DetailViewHeader(repository: Repository) {
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
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.25.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            )
        }
        Spacer(Modifier.size(10.dp))
        Text(
            text = repository.name,
            style = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        )
        Spacer(Modifier.size(5.dp))
        Text(
            text = repository.detailInfo?.description?: "",
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
        }
    }

    @Composable
    fun SimpleContentBox(
        @StringRes headerText: Int,
        tailText: String? = "",
        tailComposable: (@Composable () -> Unit)? = null,
        moreContent: (@Composable () -> Unit)? = null
    ) {
        var isMoreOpen by remember { mutableStateOf(false) }

        Card (
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            backgroundColor = MaterialTheme.colors.background,
            onClick = {
                isMoreOpen = !isMoreOpen
            },
            enabled = moreContent != null,
            elevation = 0.dp
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text (
                        text = stringResource(id = headerText),
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                    if (tailComposable != null) {
                        tailComposable()
                    } else {
                        Text(
                            text = tailText?: "",
                            style = MaterialTheme.typography.button.copy(
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    }
                }
                AnimatedVisibility(visible = (isMoreOpen)) {
                    if (moreContent != null) {
                        moreContent()
                    }
                }
            }
        }
    }

    @Composable
    fun ContributorCard(contributor: Repository.Contributor) {
        Row(
            modifier = Modifier.padding(10.dp, 6.dp, 10.dp, 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            loadImage(
                imageUrl = contributor.avatarUrl,
                placeholderResource = R.drawable.profile_img,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = contributor.login,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.25.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            )
        }
    }

    @Composable
    fun DetailViewBody(repository: Repository) {
        Spacer(modifier = Modifier.size(10.dp))
        SimpleContentBox(
            headerText = R.string.contributors,
            tailText = repository.contributors?.size.toString(),
            moreContent = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    itemsIndexed(items = repository.contributors?: emptyList()) { _, contributor ->
                        ContributorCard(contributor)
                    }
                }
            }
        )
        SimpleContentBox(
            headerText = R.string.issues,
            tailText = repository.detailInfo?.openIssuesCount.toString()
        )
        SimpleContentBox(
            headerText = R.string.watchers,
            tailText = repository.detailInfo?.watchersCount.toString()
        )
        SimpleContentBox(
            headerText = R.string.language,
            tailComposable = {
                IconText(icon = repository.language.icon, text = repository.language.name)
            }
        )
        SimpleContentBox(
            headerText = R.string.createdAt,
            tailText = repository.detailInfo?.createdAt?.toSimpleFormat()
        )
        SimpleContentBox(
            headerText = R.string.updatedAt,
            tailText = repository.detailInfo?.updatedAt?.toSimpleFormat()
        )
        SimpleContentBox(
            headerText = R.string.pushedAt,
            tailText = repository.detailInfo?.pushedAt?.toSimpleFormat()
        )
    }

    @Composable
    fun DetailViewBottom() {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                bottom = collapsedContentHeight
            )
    ) {
        if (isLoadingRepositoryDetail) {
            CircularIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 15.dp)
            ) {
                DetailViewHeader(repository)
                DetailViewBody(repository)
                DetailViewBottom()
            }
        }
    }
}

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


@Preview
@Composable
fun RepositoryDetailViewPreview() {
    val rep = Repository(
        ownerLogin = "abcd",
        ownerAvatarUrl = "https://dimg.donga.com/wps/NEWS/IMAGE/2019/04/25/95215551.1.jpg",
        name = "dfaifjl",
        fullName = "adfafeafd adfaf",
        language = "C".toDevLanguage(),
        forksCount = 10,
        stargazersCount = 1,
        detailInfo = Repository.DetailInfo(
            description = "adfadfasdf",
            watchersCount = 10,
            openIssuesCount = 5,
            pushedAt = "2011-01-26T19:06:43Z",
            createdAt = "2011-01-26T19:01:12Z",
            updatedAt = "2011-01-26T19:01:12Z"
        ),
//        Repository.ReadMe("https://raw.githubusercontent.com/octokit/octokit.rb/master/README.md")
        readMe = "",
        contributors = listOf(
            Repository.Contributor(login = "dfweer", avatarUrl = "https://github.com/images/error/octocat_happy.gif"),
            Repository.Contributor(login = "dfweer", avatarUrl = "https://github.com/images/error/octocat_happy.gif")
        )
    )
    GithubUserSearchTheme {
        RepositoryDetailView(
            isLoadingRepositoryDetail = false,
            repository = rep,
            collapsedContentHeight = 100.dp
        )
    }
}