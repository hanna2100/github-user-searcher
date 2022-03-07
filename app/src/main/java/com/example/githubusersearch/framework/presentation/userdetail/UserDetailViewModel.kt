package com.example.githubusersearch.framework.presentation.userdetail

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import coil.Coil
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val searchUserInteractors: SearchUserInteractors
) : ViewModel() {

    var isDarkImage = mutableStateOf(false)

    fun moveToSearchUserFragment(view: View?) {
        val action = UserDetailFragmentDirections
            .actionUserDetailFragmentToSearchUserFragment()
        view?.findNavController()?.navigate(action)
    }

    suspend fun checkAvatarImgIsDark(context: Context) {
        val imageUrl = "https://img2.sbs.co.kr/img/sbs_cms/PG/2019/08/07/PG32010102_w640_h360.jpg"

        val imageLoader = context.imageLoader
        val req = ImageRequest.Builder(context)
            .data(imageUrl)
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

}