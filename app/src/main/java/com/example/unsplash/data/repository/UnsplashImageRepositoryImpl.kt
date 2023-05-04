package com.example.unsplash.data.repository

import com.example.unsplash.data.local.ImageDao
import com.example.unsplash.data.models.ImageData
import com.example.unsplash.data.remote.UnsplashApi
import com.example.unsplash.domain.repository.UnsplashImageRepository
import com.example.unsplash.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class UnsplashImageRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,  // an instance of UnsplashApi interface for making network requests
    private val dao: ImageDao  // an instance of ImageDao interface for interacting with the local database
) : UnsplashImageRepository {

    /**
     * Returns a Flow that emits Resource objects containing lists of ImageData.
     * @param accessKey A string representing the Unsplash API access key.
     * @param pageNum An integer representing the page number of the image search results to fetch.
     * @return A Flow of Resource objects containing lists of ImageData.
     */
    override fun getImages(accessKey: String, pageNum: Int): Flow<Resource<List<ImageData>>> = flow {

        emit(Resource.Loading<List<ImageData>>())  // emit a loading state to show the data is being fetched

        val cachedList = dao.fetchImagesForPage(pageNum)  // get cached image data from the local database
        emit(Resource.Loading(data = cachedList))  // emit a loading state to show the cached data is being displayed

        try {
            // fetch new image data from the Unsplash API
            val imageList = api.getUnsplashImages(accessKey, pageNum)

            // save new image data to the local database
            dao.addImages(imageList.map { it.toImageData().also { data -> data.pageNum = pageNum } })

        } catch (e: HttpException) {
            // handle HTTP errors
            if (e.response()?.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                emit(Resource.Error<List<ImageData>>("Check your API Key"))
            else
                emit(Resource.Error<List<ImageData>>("Something went wrong!"))
        } catch (e: IOException) {
            // handle network errors
            emit(Resource.Error<List<ImageData>>("Check your internet connection!"))
        }

        // get updated image data from the local database
        val newImages = dao.fetchImagesForPage(pageNum)

        emit(Resource.Success(data = newImages))  // emit the updated image data as a success state
    }
}
