package com.example.githubusersearch.framework.presentation.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.common.extensions.subscribe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel
@Inject
constructor(
    private val searchUserInteractors: SearchUserInteractors
) :ViewModel() {

    var users = mutableStateListOf<User>()
    var query = mutableStateOf("")
    var page = mutableStateOf(1)

    suspend fun searchUsers(
        query: String,
        sort: String = "",
        order: String = "",
        page: Int,
    ) {
        searchUserInteractors.searchUsers(
            query = query,
            sort = sort,
            order = order,
            page = page
        ).subscribe(
            onSuccess = {
                users.addAll(it)
            },
            onError = {
                println("NetworkError = $it")
            },
            onFailure = {
                println("HttpException = $it")
            },
        )
    }

    fun clearUsers(){
        users.clear()
    }
}