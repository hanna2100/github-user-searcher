package com.example.githubusersearch.framework.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    darkSystemBar: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = MyPalette,
        typography = NanumSquareTypography,
        shapes = Shapes,
        content = content
    )

//    val systemUiController = rememberSystemUiController()
//    SideEffect {
//        systemUiController.setStatusBarColor(
//            color = PatrickBlue,
//            darkIcons = false
//        )
//        systemUiController.setNavigationBarColor(
//            color = White,
//            darkIcons = darkSystemBar
//        )
//    }
}

object DarkRippleTheme: RippleTheme {

    @Composable
    override fun defaultColor(): Color = MaterialTheme.colors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )
}