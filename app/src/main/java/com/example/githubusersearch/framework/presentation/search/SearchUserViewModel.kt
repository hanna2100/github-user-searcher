package com.example.githubusersearch.framework.presentation.search

import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
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
    var searchText = mutableStateOf("")
    var page = mutableStateOf(1)
    var loading = mutableStateOf(false)

    suspend fun searchUsers(
        searchText: String,
        sort: String = "",
        order: String = "",
        page: Int,
    ) {
        val usernameQuery = "$searchText in:login"
        loading.value = true
        println("vm loading = ${loading.value}")
        searchUserInteractors.searchUsers(
            query = usernameQuery,
            sort = sort,
            order = order,
            page = page
        ).subscribe(
            onSuccess = {
                users.addAll(it)
                loading.value = false
                println("vm loading = ${loading.value}")
            },
            onError = {
                println("NetworkError = $it")
                loading.value = false
            },
            onFailure = {
                println("HttpException = $it")
                loading.value = false
            },
        )
    }

    fun clearUsers(){
        users.clear()
    }

    fun moveToUserDetailFragment(view: View?, userName: String) {
        val action = SearchUserFragmentDirections
            .actionSearchUserFragmentToUserDetailFragment(userName)
        view?.findNavController()?.navigate(action)
    }
}