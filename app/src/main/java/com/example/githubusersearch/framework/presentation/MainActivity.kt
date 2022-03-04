package com.example.githubusersearch.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.githubusersearch.R
import com.example.githubusersearch.framework.presentation.theme.GithubUserSearchTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUserSearchTheme {
                Box(modifier = Modifier.fillMaxSize().background(Color.Yellow))
            }
        }
//        setContentView(R.layout.activity_main)
    }
}