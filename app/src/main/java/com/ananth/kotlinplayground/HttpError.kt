package com.ananth.kotlinplayground


fun main(){
    val error : HttpError = HttpError.UnAuthorized
    when (error){
        HttpError.NotFound -> TODO()
        HttpError.UnAuthorized -> TODO()
    }

    val errorEnum: HttpErrorEnum = HttpErrorEnum.NotFound
    when(errorEnum){
        HttpErrorEnum.UnAuthorized -> TODO()
        HttpErrorEnum.NotFound -> TODO()
    }
}
sealed class HttpError(val errorCode:Int){
    object UnAuthorized : HttpError(401)
    object NotFound : HttpError(404)
}

enum class HttpErrorEnum(){
    UnAuthorized(),
    NotFound()
}
