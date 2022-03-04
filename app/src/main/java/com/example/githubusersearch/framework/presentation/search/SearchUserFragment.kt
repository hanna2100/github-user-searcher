package com.example.githubusersearch.framework.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.framework.presentation.search.composable.UserSearchBar
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment: Fragment() {
    private val viewModel: SearchUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val users = viewModel.users

        viewModel.viewModelScope.launch {
            viewModel.searchUsers("hanna", "", "", 30, 1)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                GithubUserSearchTheme {
                    Box(modifier = Modifier.fillMaxSize()) {
                        UserSearchBar(
                            onUserSearch = { searchText ->

                            }
                        )
                    }
                }
            }
        }
    }

}