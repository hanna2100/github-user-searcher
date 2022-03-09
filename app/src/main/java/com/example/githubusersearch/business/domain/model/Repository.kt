package com.example.githubusersearch.business.domain.model

import androidx.annotation.DrawableRes
import com.example.githubusersearch.common.extensions.toDevLanguage

data class Repository(
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val name: String,
    val fullName: String,
    val language: DevLanguage,
    val forksCount: Int,
    val stargazersCount: Int,
    val detailInfo: DetailInfo? = null,
    val readMeMarkdownHTML: MarkdownHTML? = null,
    val contributors: List<Contributor>? = null
) {

    companion object {
        fun getEmptyRepository(): Repository {
            return Repository(
                ownerLogin = "",
                ownerAvatarUrl = "",
                name = "",
                fullName = "",
                language = "".toDevLanguage(),
                forksCount = 0,
                stargazersCount = 0,
            )
        }

        fun Repository.setContributors(c: List<Contributor>): Repository {
            return this.copy(
                contributors = c
            )
        }

        fun Repository.setMarkdownHTML(mh: MarkdownHTML): Repository {
            return this.copy(
                readMeMarkdownHTML = mh
            )
        }
    }

    data class DetailInfo(
        val description: String,
        val watchersCount: Int,
        val openIssuesCount: Int,
        val pushedAt: String,
        val createdAt: String,
        val updatedAt: String
    )

    data class MarkdownHTML(
        val html: String,
    )

    data class Contributor(
        val login: String,
        val avatarUrl: String,
        val contributions: Int,
    )
}

data class DevLanguage(@DrawableRes val icon: Int, val name: String)

