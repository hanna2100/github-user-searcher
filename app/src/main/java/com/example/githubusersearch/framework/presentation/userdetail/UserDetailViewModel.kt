package com.example.githubusersearch.framework.presentation.userdetail

import android.content.Context
import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.githubusersearch.business.domain.model.Repository
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
//    var repositories = mutableStateListOf<Repository>()
    var repositories = mutableStateOf<List<Repository>>(listOf())
    var isLoadingRepositories = mutableStateOf(false)

    fun moveToSearchUserFragment(view: View?) {
        val action = UserDetailFragmentDirections
            .actionUserDetailFragmentToSearchUserFragment()
        view?.findNavController()?.navigate(action)
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
//                repositories.addAll(it)
                repositories.value = it
                isLoadingRepositories.value = false
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
}