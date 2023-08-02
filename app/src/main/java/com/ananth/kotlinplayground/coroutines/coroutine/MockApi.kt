package com.ananth.kotlinplayground.coroutines.coroutine

import com.ananth.kotlinplayground.coroutines.coroutine.util.EndpointShouldNotBeCalledException
import okhttp3.OkHttpClient
import okhttp3.internal.Version
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MockApi {

    @GET("recent-android-version")
    suspend fun getRecentAndroidVersions(): List<AndroidVersion>

    @GET("android-version-features/{apiLevel}")
    suspend fun getAndroidVersionFeatures(@Path("apiLevel") apiLevel: Int): VersionFeatures
}

fun createMockApi(): MockApi {
    val okHttpClient = with(OkHttpClient.Builder()) {
        addInterceptor(null)
        build()
    }

    val retrofit = with(Retrofit.Builder()) {
        baseUrl("http://localhost/")
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    return retrofit.create(MockApi::class.java)
}

open class SimulateSuccessResponse : MockApi{
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions;
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}