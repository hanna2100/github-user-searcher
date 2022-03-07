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
                    isDarkImage.value = ColorUtils.calculateLuminance(it) < 0.5
                }
            }
        }
    }

    suspend fun getUser(userName: String) {
        userDetailInteractors.getUser(userName).subscribe(
            onSuccess = {
                user.value = it
            },
            onError = {

            },
            onFailure = {

            }
        )
    }

}