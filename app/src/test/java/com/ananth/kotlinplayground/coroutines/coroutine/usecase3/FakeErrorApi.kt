package com.ananth.kotlinplayground.coroutines.coroutine.usecase3

import com.ananth.kotlinplayground.coroutines.coroutine.AndroidVersion
import com.ananth.kotlinplayground.coroutines.coroutine.VersionFeatures
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi
import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeErrorApi : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw HttpException(
            Response.error<List<AndroidVersion>>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}