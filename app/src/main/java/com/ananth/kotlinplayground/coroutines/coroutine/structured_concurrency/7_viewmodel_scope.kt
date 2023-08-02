package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(){
    init {
        viewModelScope.launch {
            delay(1000) //network request
        }
    }
}