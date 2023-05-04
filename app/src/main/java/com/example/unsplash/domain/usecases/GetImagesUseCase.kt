package com.example.unsplash.domain.usecases

import com.example.unsplash.data.models.ImageData
import com.example.unsplash.domain.repository.UnsplashImageRepository
import com.example.unsplash.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: UnsplashImageRepository
) {

    // Operator function that returns a Flow of Resource<List<ImageData>>.
    // It receives a Param object which contains the access key and the page number.
    operator fun invoke(params: Param): Flow<Resource<List<ImageData>>> {

        // Retrieves a Flow from the repository to fetch the images from the API.
        // Initially emits a Resource.Loading status.
        // Then, it checks if there is any cached data available and emits it as well.
        // After that, it tries to fetch the images from the API, and if successful,
        // adds them to the database and emits a Resource.Success with the new data.
        // If any exception occurs, it emits a Resource.Error with a corresponding message.
        return repository.getImages(params.accessKey, params.pageNum)
    }

    // A data class that holds the required parameters for the use case.
    data class Param(
        val accessKey: String,
        val pageNum: Int
    )
}
