package com.example.unsplash.data.repository

import com.example.unsplash.domain.repository.AccessKeyRepository


class AccessKeyRepositoryImpl : AccessKeyRepository {

    /**
     * Returns the hardcoded Unsplash API access key.
     * @return A string representing the Unsplash API access key.
     */
    override fun getAccessKey(): String {
        return "52ed5e63ad1915fed2bbfd2326aade6b8549b050fc8367a7c105567476df2a81"
    }
}