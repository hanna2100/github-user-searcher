package com.example.githubusersearch.framework.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
    background = White,
    onBackground = SmokyBlack,
    surface = White,
    onSurface = SmokyBlack,
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

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = PatrickBlue,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = White,
            darkIcons = darkSystemBar
        )
    }
}