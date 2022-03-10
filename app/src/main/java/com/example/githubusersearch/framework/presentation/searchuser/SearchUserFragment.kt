package com.example.githubusersearch.framework.presentation.searchuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubusersearch.common.composable.ProcessDialogQueue
import com.example.githubusersearch.framework.presentation.searchuser.composable.UserListView
import com.example.githubusersearch.framework.presentation.searchuser.composable.UserSearchBar
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment: Fragment() {

    private val viewModel: SearchUserViewModel by viewModels()
    @OptIn(ExperimentalMaterialApi::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                GithubUserSearchTheme {
                    val scope = rememberCoroutineScope()
                    val scaffoldState = rememberScaffoldState()

                    Scaffold (
                        topBar = {
                            UserSearchBar(
                                onUserSearch = { targetText ->
                                    scope.launch {
                                        viewModel.setSearchText(targetText)
                                        viewModel.clearUsers()
                                        viewModel.initPage()
                                        viewModel.searchUsers(
                                            searchText = viewModel.searchText.value,
                                            page = viewModel.page.value
                                        )
                                    }
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background)
                        ) {
                            UserListView(
                                users = viewModel.users,
                                loading = viewModel.loading.value,
                                onUserClick = { userName ->
                                    viewModel.moveToUserDetailFragment(this@SearchUserFragment.view, userName)
                                },
                                onBottomReached = {
                                    scope.launch {
                                        viewModel.pageUp()
                                        viewModel.searchUsers(
                                            searchText = viewModel.searchText.value,
                                            page = viewModel.page.value
                                        )
                                    }
                                }
                            )
                        }
                        ProcessDialogQueue(
                            viewModel.dialogQueue.queue.value
                        )
                    }
                }
            }
        }
    }
}