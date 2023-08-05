package com.ananth.kotlinplayground.coroutines.coroutine.usecase3

import androidx.lifecycle.viewModelScope
import com.ananth.kotlinplayground.coroutines.coroutine.BaseViewModel
import com.ananth.kotlinplayground.coroutines.coroutine.UiState3
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.createMockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PerformNetworkRequestConcurrentlyViewModel(private val mockApi: MockApi = createMockApi()) :
    BaseViewModel<UiState3>() {

    fun performNetworkRequestSequentially() {
        uiState.value = UiState3.Loading
        try {
            viewModelScope.launch {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(29)

                val versionFeatures = listOf(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState3.Success(versionFeatures)
            }
        } catch (e: Exception) {
            uiState.value = UiState3.Error("Network request failed")
        }
    }


    fun performNetworkRequestConcurrently() {
        uiState.value = UiState3.Loading

        val oreoFeaturesDeferred = viewModelScope.async { mockApi.getAndroidVersionFeatures(27) }
        val pieFeaturesDeferred = viewModelScope.async { mockApi.getAndroidVersionFeatures(28) }
        val android10FeaturesDeferred =
            viewModelScope.async { mockApi.getAndroidVersionFeatures(29) }

        viewModelScope.launch {
            try {
                val versionFeatures =
                    listOf(
                        oreoFeaturesDeferred,
                        pieFeaturesDeferred,
                        android10FeaturesDeferred
                    ).awaitAll()
                uiState.value = UiState3.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState3.Error("Network request failed")
            }
        }
    }
}