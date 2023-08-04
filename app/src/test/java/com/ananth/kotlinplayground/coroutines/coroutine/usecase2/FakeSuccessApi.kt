package com.ananth.kotlinplayground.coroutines.coroutine.usecase2

import com.ananth.kotlinplayground.coroutines.coroutine.*
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi

class FakeSuccessApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
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