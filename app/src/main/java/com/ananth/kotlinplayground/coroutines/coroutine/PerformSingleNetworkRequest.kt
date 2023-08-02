package com.ananth.kotlinplayground.coroutines.coroutine

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PerformSingleNetworkRequest(val mockApi: MockApi = createMockApi()) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersion = mockApi.getRecentAndroidVersions()
                uiState.value = UiState.Success(recentAndroidVersion)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }
}