package com.ananth.kotlinplayground.coroutines.coroutine

sealed interface UiState{
    object Loading:UiState
    data class Success(val recentVersion:List<AndroidVersion>) : UiState
    data class Error(val error:String) : UiState
}

sealed interface UiState2{
    object Loading:UiState2
    data class Success(val versionFeatures: VersionFeatures) : UiState2
    data class Error(val error:String) : UiState2
}