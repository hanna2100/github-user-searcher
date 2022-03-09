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
import com.example.githubusersearch.business.domain.model.ReadMe
import com.example.githubusersearch.business.domain.model.Repository
import com.example.githubusersearch.business.domain.model.Repository.Companion.setContributors
import com.example.githubusersearch.business.domain.model.Repository.Companion.setMarkdownHTML
import com.example.githubusersearch.business.domain.model.User
import com.example.githubusersearch.business.interactors.userdetail.UserDetailInteractors
import com.example.githubusersearch.common.extensions.subscribe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val userDetailInteractors: UserDetailInteractors
) : ViewModel() {

    var isDarkImage = mutableStateOf(false)

    var user = mutableStateOf(User.getEmptyUser())
    var isLoadingUser = mutableStateOf(false)

    var repositories = mutableStateOf<List<Repository>>(listOf())
    var isLoadingRepositories = mutableStateOf(false)

    val repositoryDetail = mutableStateOf(Repository.getEmptyRepository())
    var isLoadingRepositoryDetail = mutableStateOf(false)
    var isReadMeMarkdownRenderReady = mutableStateOf(false)

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
                println("getUser onSuccess $it")
            },
            onError = {
                isLoadingUser.value = false
            },
            onFailure = {
                isLoadingUser.value = false
            }
        )
    }

    suspend fun getRepositories(userName: String) {
        isLoadingRepositories.value = true

        userDetailInteractors.getRepositories(userName).subscribe(
            onSuccess = {
                repositories.value = it
                isLoadingRepositories.value = false
                println("getRepositories onSuccess ${it.size}")
            },
            onError = {
                println("getRepositories onError $it")
                isLoadingRepositories.value = false
            },
            onFailure = {
                println("getRepositories onFailure $it")
                isLoadingRepositories.value = false
            }
        )
    }

    fun initLoadingValue() {
        isLoadingRepositoryDetail.value = true
        isReadMeMarkdownRenderReady.value = false
    }

    suspend fun getRepository(owner: String, repo: String) {
        userDetailInteractors.getRepository(owner, repo).subscribe(
            onSuccess = {
                repositoryDetail.value = it
                isLoadingRepositoryDetail.value = false
                println("getRepository onSuccess $it")
            },
            onError = {
                isLoadingRepositoryDetail.value = false
                println("getRepository onError $it")
            },
            onFailure = {
                isLoadingRepositoryDetail.value = false
                println("getRepository onFailure $it")
            }
        )
    }

    suspend fun getContributors(owner: String, repo: String) {
        userDetailInteractors.getContributors(owner, repo).subscribe(
            onSuccess = {
                repositoryDetail.value = repositoryDetail.value.setContributors(it)
            },
            onError = {

            },
            onFailure = {

            }
        )
    }


    suspend fun getReadMe(
        owner: String,
        repo: String,
    ) {
        userDetailInteractors.getReadMe(owner, repo).subscribe(
            onSuccess = {
                renderMarkDown(it.content)
            },
            onError = {

            },
            onFailure = {

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

            },
            onFailure = {

            }
        )
    }

}