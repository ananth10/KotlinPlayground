package com.ananth.kotlinplayground.coroutines.coroutine.usecase2

import androidx.lifecycle.viewModelScope
import com.ananth.kotlinplayground.coroutines.coroutine.BaseViewModel
import com.ananth.kotlinplayground.coroutines.coroutine.UiState2
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.MockApi
import com.ananth.kotlinplayground.coroutines.coroutine.usecase1.createMockApi
import kotlinx.coroutines.launch

class Perform2SequentialRequest(private val mockApi: MockApi = createMockApi()) :
    BaseViewModel<UiState2>() {

    fun perform2SequentialRequest() {
        uiState.value = UiState2.Loading

        viewModelScope.launch {
            try {
                val recentAndroidVersion = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentAndroidVersion.last()

                val featuresOfMostRecentAndroidVersion =
                    mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)
                uiState.value = UiState2.Success(featuresOfMostRecentAndroidVersion)
            } catch (e: Exception) {
                uiState.value = UiState2.Error("Network request failed!")
            }
        }
    }
}