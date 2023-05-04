package com.example.unsplash.presentation.popular

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.unsplash.presentation.theme.UnsplashDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // This annotation is used to mark MainActivity as an entry point for Hilt dependency injection
    // This allows Hilt to inject dependencies into this activity
    @InternalCoroutinesApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // This is the top-level composable function that defines the UI layout of the app
            // It sets the theme and background color, and includes the root composable PopularRootLayout
            UnsplashDemoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PopularRootLayout()
                }
            }
        }
    }
}
