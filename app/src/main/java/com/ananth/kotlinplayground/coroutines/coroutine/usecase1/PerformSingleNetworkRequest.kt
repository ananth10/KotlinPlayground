package com.ananth.kotlinplayground.coroutines.coroutine.usecase1

import androidx.lifecycle.viewModelScope
import com.ananth.kotlinplayground.coroutines.coroutine.BaseViewModel
import com.ananth.kotlinplayground.coroutines.coroutine.UiState
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