package com.ananth.kotlinplayground.coroutines.coroutine.usecase2

import com.ananth.kotlinplayground.coroutines.coroutine.*
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi
import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeFeatureErrorApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> throw HttpException(
                Response.error<VersionFeatures>(
                    500,
                    ResponseBody.create(MediaType.parse("application/json"), "")
                )
            )
            else -> {
                throw IllegalArgumentException("api level is not found")
            }
        }
    }
}