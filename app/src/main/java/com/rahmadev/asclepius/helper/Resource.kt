package com.rahmadev.asclepius.helper

sealed class Resource<R>(val data: R? = null, val message: String? = null, val errorCode: Int? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, errorCode: Int? = null, data: T? = null) : Resource<T>(data, message, errorCode)
}