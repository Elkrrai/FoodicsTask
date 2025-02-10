package com.example.foodicstask.core.domain.util

sealed interface DataError {
    sealed interface NetworkError : DataError {
        data object RequestTimeout : NetworkError
        data object TooManyRequests : NetworkError
        data object NoInternet : NetworkError
        data object ServerError : NetworkError
        data object Serialization : NetworkError
        data object Unknown : NetworkError
    }

    data object NoSearchResult : DataError

    data object LocalError : DataError
}
