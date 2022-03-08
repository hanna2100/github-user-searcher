package com.example.githubusersearch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val defaultInfo: DefaultInfo,
    val detailInfo: DetailInfo? = null,
): Parcelable {
    companion object {
        fun getEmptyUser(): User {
            return User(DefaultInfo("", 0, ""))
        }
    }

    @Parcelize
    data class DefaultInfo(
        val login: String,
        val id: Int,
        val avatarUrl: String,
    ): Parcelable

    @Parcelize
    data class DetailInfo(
        val bio: String,
        val blog: String,
        val createAt: String,
        val followers: Int,
        val following: Int,
        val name: String,
        val location: String,
        val email: String
    ): Parcelable
}