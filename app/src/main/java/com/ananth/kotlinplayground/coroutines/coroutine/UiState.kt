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

sealed interface UiState3{
    object Loading:UiState3
    data class Success(val versionFeatures: List<VersionFeatures>) : UiState3
    data class Error(val error:String) : UiState3
}