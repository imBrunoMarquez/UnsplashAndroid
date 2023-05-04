package com.example.unsplash

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt annotation to enable Hilt dependency injection for the application
@HiltAndroidApp
class UnsplashApp :Application()