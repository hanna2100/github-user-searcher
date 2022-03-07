package com.example.githubusersearch.framework.presentation.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import com.example.githubusersearch.framework.presentation.userdetail.composable.CollapsableToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment: Fragment() {
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.viewModelScope.launch {
            viewModel.getUser(getUserName())
            viewModel.checkAvatarImgIsDark(
                context = requireContext(),
                avatarImgUrl = viewModel.user.value.defaultInfo.avatarUrl
            )
        }

        viewModel.viewModelScope.launch {
            viewModel.getRepositories(getUserName())
        }

        return ComposeView(requireContext()).apply {
            setContent {
                GithubUserSearchTheme {
                    val isDarkAvatar = viewModel.isDarkImage.value
                    val user = viewModel.user.value
                    val repositories = viewModel.repositories.value

                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                    ) {
                        CollapsableToolbar(
                            onBackButtonClick = {
                                viewModel.moveToSearchUserFragment(this@UserDetailFragment.view)
                            },
                            textColorOverAvatar = if (isDarkAvatar) Color.White else Color.Black,
                            user = user,
                            repositories = repositories?: emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun getUserName(): String {
        val args: UserDetailFragmentArgs by navArgs()
        return args.userName
    }

}