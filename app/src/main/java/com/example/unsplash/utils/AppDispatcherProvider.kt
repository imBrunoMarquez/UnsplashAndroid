package com.example.unsplash.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// A custom implementation of the `DispatcherProvider` interface that provides dispatchers for different contexts
class AppDispatcherProvider : DispatcherProvider {
    // CoroutineDispatcher for main thread operations
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    // CoroutineDispatcher for performing I/O operations (such as file or network operations)
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    // CoroutineDispatcher for CPU-intensive operations that don't require to run on the main thread
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    // CoroutineDispatcher that runs unconfined on a thread pool of shared threads
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}
