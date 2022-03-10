package com.example.githubusersearch.framework.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import com.example.githubusersearch.common.composable.GenericDialog
import com.example.githubusersearch.common.util.GenericDialogInfo
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

private val MyPalette = darkColors(
    primary = PatrickBlue,
    primaryVariant = CetaceanBlue,
    onPrimary = White,
    secondary = VioletsAreBlue,
    secondaryVariant = InterdimensionalBlue,
    onSecondary = White,
    error = MaximumRed,
    onError = White,
    background = CetaceanBlue,
    onBackground = White,
    surface = Color.Black,
    onSurface = White,
)

@Composable
fun GithubUserSearchTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = MyPalette,
        typography = NanumSquareTypography,
        shapes = Shapes,
        content = content
    )

    /*
        systemUiController 사용시 preview 렌더링 오류가 있어 추가함
        java.lang.IllegalArgumentException: The Compose View must be hosted in an Activity with a Window!
    */
    if (!LocalInspectionMode.current) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = PatrickBlue,
                darkIcons = false
            )
            systemUiController.setNavigationBarColor(
                color = CetaceanBlue,
                darkIcons = false
            )
        }
    }
}

object LightRippleTheme: RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.White

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.White,
        lightTheme = !isSystemInDarkTheme()
    )
}
