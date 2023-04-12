package com.ananth.kotlinplayground

import androidx.lifecycle.MutableLiveData


fun main(){

    val response: MutableLiveData<NetworkResult<Result>> = MutableLiveData()

}

sealed class NetworkResult<T>(val data : T? =null, val message: String?=""){
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(data: T?=null, message: String?) : NetworkResult<T>(data,message)
    class Loading<T> : NetworkResult<T>()
}

data class Result(val data: String?=null)

private fun fetchAPI(response : MutableLiveData<NetworkResult<Result>>){

    response.value = NetworkResult.Loading()
    response.value = NetworkResult.Success(data = Result(""))
    response.value = NetworkResult.Error(null, "error")
}