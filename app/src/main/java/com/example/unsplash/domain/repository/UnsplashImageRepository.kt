package com.example.unsplash.domain.repository

import com.example.unsplash.data.models.ImageData
import com.example.unsplash.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UnsplashImageRepository {
    /**
     * This is an interface UnsplashImageRepository with a single function getImages which takes in an accessKey string
     * and a pageNum integer and returns a Flow of Resource of List<ImageData>
     */
    fun getImages(accessKey: String, pageNum: Int): Flow<Resource<List<ImageData>>>
}