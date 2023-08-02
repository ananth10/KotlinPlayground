package com.ananth.kotlinplayground.coroutines.coroutine

sealed interface UiState{
    object Loading:UiState
    data class Success(val recentVersion:List<AndroidVersion>) : UiState
    data class Error(val error:String) : UiState
}
