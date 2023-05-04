package com.example.unsplash.domain.usecases

import com.example.unsplash.domain.repository.AccessKeyRepository
import javax.inject.Inject

/**
 * GetKeyUseCase is a use case class that provides access to the access key
 * needed to access the Unsplash API. It takes an instance of AccessKeyRepository
 * as a dependency and provides a method to get the access key from it.
 *
 * @param repository An instance of AccessKeyRepository to retrieve the access key from
 */
class GetKeyUseCase @Inject constructor(
    private val repository: AccessKeyRepository
) {

    /**
     * This method retrieves the access key from the repository and returns it.
     *
     * @return The access key as a String
     */
    operator fun invoke() = repository.getAccessKey()
}
