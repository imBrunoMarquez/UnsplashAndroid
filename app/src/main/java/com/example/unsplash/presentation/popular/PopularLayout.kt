package com.example.unsplash.presentation.popular

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.unsplash.R
import com.example.unsplash.data.models.ImageData
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

//Import statements
@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun PopularRootLayout() {
    // Obtain MainViewModel using Hilt
    val viewModel: MainViewModel = hiltViewModel()
    // Collect the UI state from the ViewModel using StateFlow
    val uiState by viewModel.uiState.collectAsState()

    // Remember the state of the scaffold
    val scaffoldState = rememberScaffoldState()
    // Remember the state of the LazyList
    val listState = rememberLazyListState()

    // Calculate whether or not to load more items based on the LazyList state
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            //Make the fetch call before last 2 items are visible
            lastVisibleItemIndex > (totalItemsNumber - 2)
        }
    }

    // Launch a coroutine that observes the loadMore state and fetches more images if needed
    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { viewModel.fetchPopularImages() }
    }

    // Launch a coroutine that observes the eventFlow from the ViewModel and shows a snackbar if needed
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    // Scaffold composable that wraps the content
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display the images in a LazyVerticalGrid if there are any
            if (uiState.imageList.isNotEmpty()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    state = listState,
                    contentPadding = PaddingValues(4.dp, 4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(uiState.imageList) {
                        ImageItem(imageData = it)
                    }
                }
            }
            // Display a loading indicator if needed
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
        }
    }
}

// Composable function that displays an image and, when clicked, shows a fullscreen version of the image
@ExperimentalMaterialApi
@Composable
fun ImageItem(imageData: ImageData) {
    val context = LocalContext.current
    // Remember whether or not to show the fullscreen version of the image
    val showFullScreenImage = remember { mutableStateOf(false) }

    // Display the image in a Card
    Card(
        elevation = 4.dp,
        onClick = {
            // When the Card is clicked, show the fullscreen version of the image
            showFullScreenImage.value = true
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageData.url,
                // Use crossfade animation when loading images
                builder = {
                    crossfade(true)
                    // Show a placeholder image while loading
                    placeholder(R.drawable.ic_loading)
                    // Show an error image if the image cannot be loaded
                    error(R.drawable.ic_error)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                // Clip the image into a rounded shape
                .clip(RoundedCornerShape(5.dp))
                // Make the image aspect ratio 1:1 (square)
                .aspectRatio(1f)
                // Add a gray background behind the image
                .background(Color.Gray)
        )
    }

    // If showFullScreenImage is true, display the image in a Dialog
    if (showFullScreenImage.value) {
        Dialog(onDismissRequest = { showFullScreenImage.value = false }) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Show the image in fullscreen, with padding around it
                Image(
                    painter = rememberImagePainter(
                        data = imageData.url,
                        // Use crossfade animation when loading images
                        builder = {
                            crossfade(true)
                            // Show a placeholder image while loading
                            placeholder(R.drawable.ic_loading)
                            // Show an error image if the image cannot be loaded
                            error(R.drawable.ic_error)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

                // Add a close button at the top right corner of the Dialog
                IconButton(
                    onClick = { showFullScreenImage.value = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Fullscreen Image",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
