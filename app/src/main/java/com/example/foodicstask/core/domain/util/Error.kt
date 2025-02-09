package com.example.foodicstask.core.domain.util

sealed interface Error {
    sealed interface NetworkError : Error {
        data object RequestTimeout : NetworkError
        data object TooManyRequests : NetworkError
        data object NoInternet : NetworkError
        data object ServerError : NetworkError
        data object Serialization : NetworkError
        data object Unknown : NetworkError
    }

    data object NoSearchResult : Error
}
