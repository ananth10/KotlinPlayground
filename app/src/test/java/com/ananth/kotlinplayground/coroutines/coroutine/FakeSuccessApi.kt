package com.ananth.kotlinplayground.coroutines.coroutine

import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException

class FakeSuccessApi : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}
