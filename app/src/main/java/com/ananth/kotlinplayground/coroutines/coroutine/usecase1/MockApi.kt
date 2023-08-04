package com.ananth.kotlinplayground.coroutines.coroutine.usecase1

import com.ananth.kotlinplayground.coroutines.coroutine.AndroidVersion
import com.ananth.kotlinplayground.coroutines.coroutine.VersionFeatures
import okhttp3.OkHttpClient
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
