package com.example.githubusersearch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val defaultInfo: DefaultInfo,
    val detailInfo: DetailInfo? = null,
): Parcelable

@Parcelize
data class DefaultInfo(
    val login: String,
    val id: Int,
    val avatarUrl: String,
): Parcelable

@Parcelize
data class DetailInfo(
    val followers: Int,
    val following: Int,
    val reposUrl: String,
    val name: String,
    val location: String,
    val email: String
): Parcelable