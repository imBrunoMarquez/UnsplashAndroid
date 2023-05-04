package com.example.unsplash.utils

import kotlinx.coroutines.CoroutineDispatcher

// Interface defining different dispatchers to be used for coroutines
interface DispatcherProvider {
    // Dispatcher for the main thread
    val main: CoroutineDispatcher
    // Dispatcher for IO operations, such as network requests or file operations
    val io: CoroutineDispatcher
    // Dispatcher for CPU-intensive operations, such as image processing or data parsing
    val default: CoroutineDispatcher
    // Dispatcher with no specific thread or thread pool, used for debugging or testing purposes
    val unconfined: CoroutineDispatcher
}