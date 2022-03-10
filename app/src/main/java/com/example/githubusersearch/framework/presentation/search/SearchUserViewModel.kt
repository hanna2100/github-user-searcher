package com.example.githubusersearch.framework.presentation.search

import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.common.extensions.subscribe
import com.example.githubusersearch.common.util.DialogQueue
import com.example.githubusersearch.common.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel
@Inject
constructor(
    private val searchUserInteractors: SearchUserInteractors,
    private val resourcesProvider: ResourcesProvider
) :ViewModel() {

    var users = mutableStateListOf<User>()
    var searchText = mutableStateOf("")
    var page = mutableStateOf(1)
    var loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    suspend fun searchUsers(
        searchText: String,
        sort: String = "",
        order: String = "",
        page: Int
    ) {
        val usernameQuery = "$searchText in:login"
        loading.value = true
        searchUserInteractors.searchUsers(
            query = usernameQuery,
            sort = sort,
            order = order,
            page = page
        ).subscribe(
            onSuccess = {
                if (it.isEmpty()) {
                    dialogQueue.appendErrorMessage(
                        resourcesProvider.getString(R.string.user_search),
                        resourcesProvider.getString(R.string.no_search_result)
                    )
                } else {
                    users.addAll(it)
                }
                loading.value = false
            },
            onError = {
                dialogQueue.appendErrorMessage(
                    resourcesProvider.getString(R.string.user_search),
                    it.message
                )
                loading.value = false
            },
            onFailure = {
                dialogQueue.appendErrorMessage(
                    resourcesProvider.getString(R.string.user_search),
                    it.message()
                )
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

    fun initPage() {
        page.value = 1
    }

    fun setSearchText(targetText: String) {
        searchText.value = targetText
    }

    fun pageUp() {
        page.value++
    }
}