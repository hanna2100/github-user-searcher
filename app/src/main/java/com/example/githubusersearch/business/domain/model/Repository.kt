package com.example.githubusersearch.business.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Repository(
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val name: String,
    val fullName: String,
    val language: DevLanguage,
    val forksCount: Int,
    val stargazersCount: Int,
)

data class DevLanguage(@DrawableRes val icon: Int, val name: String)
