package com.example.unsplash.data.remote

import com.example.unsplash.data.models.ImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    /**
     * Retrofit GET request to fetch Unsplash images from the API.
     * @param accessKey The Unsplash API access key to authorize the request.
     * @param page The page number of the images to fetch.
     * @param perPage The number of images to fetch per page (default is 10).
     * @return A list of ImageDto objects representing the images fetched from the API.
     */
    @GET("/photos/")
    suspend fun getUnsplashImages(
        @Query("client_id") accessKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
    ): List<ImageDto>

    companion object {
        // The base URL for the Unsplash API
        const val BASE_URL = "https://api.unsplash.com/"
    }
}
