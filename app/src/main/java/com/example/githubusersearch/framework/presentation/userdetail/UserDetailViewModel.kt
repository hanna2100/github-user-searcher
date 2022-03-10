package com.example.githubusersearch.framework.presentation.userdetail

import android.content.Context
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.Repository.Companion.setContributors
import com.example.githubusersearch.business.domain.model.Repository.Companion.setMarkdownHTML
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.userdetail.UserDetailInteractors
import com.example.githubusersearch.common.extensions.subscribe
import com.example.githubusersearch.common.util.DialogQueue
import com.example.githubusersearch.common.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val userDetailInteractors: UserDetailInteractors,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    var isDarkImage = mutableStateOf(false)

    var user = mutableStateOf(User.getEmptyUser())
    var isLoadingUser = mutableStateOf(false)

    var repositories = mutableStateOf<List<Repository>>(listOf())
    var isLoadingRepositories = mutableStateOf(false)

    val repositoryDetail = mutableStateOf(Repository.getEmptyRepository())
    var isLoadingRepositoryDetail = mutableStateOf(false)
    var isReadMeMarkdownRenderReady = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    fun moveToSearchUserFragment(view: View?) {
        view?.findNavController()?.popBackStack()
    }

    suspend fun checkAvatarImgIsDark(context: Context, avatarImgUrl: String) {
        val imageLoader = context.imageLoader
        val req = ImageRequest.Builder(context)
            .data(avatarImgUrl)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(req)

        if (result is SuccessResult) {
            val bitmap = result.drawable.toBitmap()
            Palette.from(bitmap).generate { palette ->
                val rgb = palette?.dominantSwatch?.rgb
                rgb?.let {
                    isDarkImage.value = ColorUtils.calculateLuminance(it) < 0.4
                }
            }
        }
    }

    suspend fun getUser(userName: String) {
        isLoadingUser.value = true
        userDetailInteractors.getUser(userName).subscribe(
            onSuccess = {
                user.value = it
                isLoadingUser.value = false
            },
            onError = {
                this::getUser.name
                isLoadingUser.value = false
                loadErrorDialog(this::getUser.name, it.message)
            },
            onFailure = {
                isLoadingUser.value = false
                loadErrorDialog(this::getUser.name, it.message())
            }
        )
    }

    suspend fun getRepositories(userName: String) {
        isLoadingRepositories.value = true

        userDetailInteractors.getRepositories(userName).subscribe(
            onSuccess = {
                repositories.value = it
                isLoadingRepositories.value = false
            },
            onError = {
                isLoadingRepositories.value = false
                loadErrorDialog(this::getRepositories.name, it.message)
            },
            onFailure = {
                isLoadingRepositories.value = false
                loadErrorDialog(this::getRepositories.name, it.message())
            }
        )
    }

    fun initLoadingValue() {
        isReadMeMarkdownRenderReady.value = false
        isLoadingRepositoryDetail.value = true
    }

    suspend fun getRepository(owner: String, repo: String) {
        isLoadingRepositoryDetail.value = true
        userDetailInteractors.getRepository(owner, repo).subscribe(
            onSuccess = {
                repositoryDetail.value = it
                isLoadingRepositoryDetail.value = false
            },
            onError = {
                isLoadingRepositoryDetail.value = false
                loadErrorDialog(this::getRepository.name, it.message)
            },
            onFailure = {
                isLoadingRepositoryDetail.value = false
                loadErrorDialog(this::getRepository.name, it.message())
            }
        )
    }

    suspend fun getContributors(owner: String, repo: String) {
        isLoadingRepositoryDetail.value = true
        userDetailInteractors.getContributors(owner, repo).subscribe(
            onSuccess = {
                repositoryDetail.value = repositoryDetail.value.setContributors(it)
                isLoadingRepositoryDetail.value = false
            },
            onError = {
                isLoadingRepositoryDetail.value = false
                loadErrorDialog(this::getContributors.name, it.message)
            },
            onFailure = {
                isLoadingRepositoryDetail.value = false
                loadErrorDialog(this::getContributors.name, it.message())
            }
        )
    }


    suspend fun getReadMe(
        owner: String,
        repo: String,
    ) {
        isLoadingRepositoryDetail.value = true
        userDetailInteractors.getReadMe(owner, repo).subscribe(
            onSuccess = {
                isLoadingRepositoryDetail.value = false
                renderMarkDown(it.content)
            },
            onError = {
                isLoadingRepositoryDetail.value = false
                if (it.code != 404) { //READ ME 가 없는 경우 제외
                    loadErrorDialog(this::getReadMe.name, it.message)
                }
            },
            onFailure = {
                isLoadingRepositoryDetail.value = false
                loadErrorDialog(this::getReadMe.name, it.message())
            }
        )
    }

    suspend fun renderMarkDown(content: String) {
        userDetailInteractors.renderMarkDown(content).subscribe(
            onSuccess = {
                repositoryDetail.value = repositoryDetail.value.setMarkdownHTML(it)
                isReadMeMarkdownRenderReady.value = true
            },
            onError = {
                loadErrorDialog(this::renderMarkDown.name, it.message)
            },
            onFailure = {
                loadErrorDialog(this::renderMarkDown.name, it.message())
            }
        )
    }

    private fun loadErrorDialog(methodName: String, description: String?) {
        dialogQueue.appendErrorMessage(
            "$methodName ${resourcesProvider.getString(R.string.error)}",
            description?: ""
        )
    }
}