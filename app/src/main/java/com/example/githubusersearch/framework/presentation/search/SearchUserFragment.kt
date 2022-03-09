package com.example.githubusersearch.framework.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubusersearch.framework.presentation.search.composable.UserListView
import com.example.githubusersearch.framework.presentation.search.composable.UserSearchBar
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment: Fragment() {
    private val viewModel: SearchUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                GithubUserSearchTheme {
                    var users = viewModel.users
                    var page = viewModel.page.value
                    var searchText = viewModel.searchText.value

                    val scope = rememberCoroutineScope()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        UserSearchBar(
                            onUserSearch = { targetText ->
                                scope.launch {
                                    searchText = targetText
                                    viewModel.clearUsers()
                                    viewModel.searchUsers(searchText = searchText, page = page)
                                }
                            }
                        )
                        UserListView(
                            users = users,
                            loading = viewModel.loading.value,
                            onUserClick = { userName ->
                                viewModel.moveToUserDetailFragment(this@SearchUserFragment.view, userName)
                            },
                            onBottomReached = {
                                scope.launch {
                                    page++
                                    viewModel.searchUsers(searchText = searchText, page = page)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}