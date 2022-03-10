package com.example.githubusersearch.framework.presentation.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.example.githubusersearch.common.composable.ProcessDialogQueue
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme
import com.example.githubusersearch.framework.presentation.userdetail.composable.CollapsableToolbar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment: Fragment() {
    private val viewModel: UserDetailViewModel by viewModels()
    private var callback: OnBackPressedCallback? = null

    @OptIn(ExperimentalPagerApi::class)
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
                    val isLoadingRepositories = viewModel.isLoadingRepositories.value
                    val isLoadingUser = viewModel.isLoadingUser.value
                    val repositoryDetail = viewModel.repositoryDetail.value
                    val isLoadingRepositoryDetail = viewModel.isLoadingRepositoryDetail.value
                    val isReadMeMarkdownRenderReady = viewModel.isLoadingReadMeMarkdown.value

                    val pagerState = rememberPagerState()
                    val scope = rememberCoroutineScope()

                    setOnBackPressedCallback(scope, pagerState)

                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                    ) {
                        CollapsableToolbar(
                            onBackButtonClick = {
                                viewModel.moveToSearchUserFragment(this@UserDetailFragment.view)
                            },
                            textColorOverAvatar = if (isDarkAvatar) Color.White else Color.Black,
                            user = user,
                            isLoadingUser = isLoadingUser,
                            repositories = repositories,
                            isLoadingRepositories = isLoadingRepositories,
                            onRepositoryClick = { owner, repo ->
                                scope.launch {
                                    viewModel.initLoadingValue()
                                    pagerState.animateScrollToPage(1)
                                    viewModel.getRepository(owner, repo)
                                    viewModel.getContributors(owner, repo)
                                    viewModel.getReadMe(owner, repo)
                                }
                            },
                            repositoryDetail = repositoryDetail,
                            isLoadingRepositoryDetail = isLoadingRepositoryDetail,
                            repositoryViewPagerState = pagerState,
                            isLoadingReadMeMarkdown = isReadMeMarkdownRenderReady
                        )
                    }
                    ProcessDialogQueue(
                        viewModel.dialogQueue.queue.value
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    private fun setOnBackPressedCallback(scope: CoroutineScope, pagerState: PagerState) {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                scope.launch {
                    if (pagerState.currentPage != 0) {
                        pagerState.animateScrollToPage(0)
                    } else {
                        viewModel.moveToSearchUserFragment(view)
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            callback!!
        )
    }

    override fun onDetach() {
        super.onDetach()
        callback?.remove()
    }

    private fun getUserName(): String {
        val args: UserDetailFragmentArgs by navArgs()
        return args.userName
    }

}