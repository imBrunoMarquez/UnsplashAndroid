package com.example.unsplash.presentation.popular

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.data.models.ImageData
import com.example.unsplash.domain.usecases.GetImagesUseCase
import com.example.unsplash.domain.usecases.GetKeyUseCase
import com.example.unsplash.utils.DispatcherProvider
import com.example.unsplash.utils.Resource

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getKeyUseCase: GetKeyUseCase,  // Dependency injection for GetKeyUseCase
    private val getImagesUseCase: GetImagesUseCase,  // Dependency injection for GetImagesUseCase
    private val dispatcherProvider: DispatcherProvider  // Dependency injection for DispatcherProvider
) : ViewModel() {

    private var pageNum = 1  // Keep track of the page number for pagination

    private val imageDataList = ArrayList<ImageData>()  // List to store retrieved image data

    // MutableStateFlow to store the UI state
    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()  // Expose the UI state as an immutable StateFlow

    // MutableSharedFlow to emit UI events
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()  // Expose the UI events as an immutable SharedFlow

    init {
        fetchPopularImages()  // Fetch popular images on initialization
    }

    fun fetchPopularImages() {
        viewModelScope.launch(dispatcherProvider.io) {  // Launch a coroutine on the I/O dispatcher
            getImagesUseCase(GetImagesUseCase.Param(getKeyUseCase(), pageNum++))  // Invoke the GetImagesUseCase with the access key and page number
                .collect { result ->  // Collect the Flow of Resource objects
                    _uiState.update {  // Update the UI state based on the Resource
                        when (result) {
                            is Resource.Success -> {
                                // Append the retrieved images to the existing list, and update the UI state with it
                                imageDataList.addAll(result.data ?: emptyList())
                                it.copy(imageList = imageDataList, isLoading = false, errorMessage = null)
                            }
                            is Resource.Error ->{
                                // Emit a ShowSnackbar event with the error message, and update the UI state with the error message
                                _eventFlow.emit(UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                ))
                                it.copy(imageList = imageDataList, isLoading = false, errorMessage = result.message)
                            }
                            is Resource.Loading ->
                                // Update the UI state with the loading state
                                it.copy(imageList = imageDataList, isLoading = true, errorMessage = null)
                        }
                    }
                }
        }
    }
}

data class UiState(
    val isLoading: Boolean = true,
    val imageList: List<ImageData> = emptyList(),
    val errorMessage: String? = null
)

sealed class UIEvent {
    data class ShowSnackbar(val message: String): UIEvent()  // Event for showing a Snackbar with a message
}
