package com.ananth.kotlinplayground.coroutines.coroutine.usecase3

import com.ananth.kotlinplayground.coroutines.coroutine.AndroidVersion
import com.ananth.kotlinplayground.coroutines.coroutine.VersionFeatures
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesAndroid10
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesOreo
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesPie
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi
import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeSuccessApi(private val responseDelay:Long = 0L) : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw EndpointShouldNotBeCalledException()
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> mockVersionFeaturesAndroid10
            else -> {
                throw IllegalArgumentException("api level is not found")
            }
        }
    }

}