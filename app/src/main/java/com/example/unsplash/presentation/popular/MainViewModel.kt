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
    // Dependency injection for GetKeyUseCase
    private val getKeyUseCase: GetKeyUseCase,
    // Dependency injection for GetImagesUseCase
    private val getImagesUseCase: GetImagesUseCase,
    // Dependency injection for DispatcherProvider
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    // Keep track of the page number for pagination
    private var pageNum = 1

    // List to store retrieved image data
    private val imageDataList = ArrayList<ImageData>()

    // MutableStateFlow to store the UI state
    private val _uiState = MutableStateFlow<UiState>(UiState())
    // Expose the UI state as an immutable StateFlow
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // MutableSharedFlow to emit UI events
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    // Expose the UI events as an immutable SharedFlow
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // Fetch popular images on initialization
        fetchPopularImages()
    }

    fun fetchPopularImages() {
        // Launch a coroutine on the I/O dispatcher
        viewModelScope.launch(dispatcherProvider.io) {
            // Invoke the GetImagesUseCase with the access key and page number
            getImagesUseCase(GetImagesUseCase.Param(getKeyUseCase(), pageNum++))
                // Collect the Flow of Resource objects
                .collect { result ->
                    // Update the UI state based on the Resource
                    _uiState.update {
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
    // Event for showing a Snackbar with a message
    data class ShowSnackbar(val message: String): UIEvent()
}
