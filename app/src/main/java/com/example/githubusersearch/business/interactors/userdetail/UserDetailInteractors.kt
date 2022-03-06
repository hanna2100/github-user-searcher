package com.example.githubusersearch.business.interactors.userdetail

import com.example.githubusersearch.business.data.network.abstraction.GithubDataSource

class UserDetailInteractors(
    private val githubDataSource: GithubDataSource
) {
    suspend fun getUser() {
    }
}