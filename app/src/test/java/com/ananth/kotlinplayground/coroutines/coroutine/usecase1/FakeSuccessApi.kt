package com.ananth.kotlinplayground.coroutines.coroutine.usecase1

import com.ananth.kotlinplayground.coroutines.coroutine.AndroidVersion
import com.ananth.kotlinplayground.coroutines.coroutine.VersionFeatures
import com.ananth.kotlinplayground.coroutines.coroutine.mockAndroidVersions
import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException

class FakeSuccessApi : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}
