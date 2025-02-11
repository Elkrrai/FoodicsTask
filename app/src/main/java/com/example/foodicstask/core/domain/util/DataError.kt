package com.example.foodicstask.core.domain.util

/**
 * Represents different types of errors that can occur during
 * data operations allowing for exhaustive error handling.
 */
sealed interface DataError {

    /**
     * Represents errors that occur during network operations.
     * provides specific error types related to network communication.
     */
    sealed interface NetworkError : DataError {
        /**
         * Represents a timeout error during a network request.
         */
        data object RequestTimeout : NetworkError

        /**
         * Represents an error when too many requests have been made to the server.
         */
        data object TooManyRequests : NetworkError

        /**
         * Represents an error when there is no internet connection.
         */
        data object NoInternet : NetworkError

        /**
         * Represents an error when the server returns an error response (5xx status code).
         */
        data object ServerError : NetworkError

        /**
         * Represents an error during the serialization or deserialization of data.
         */
        data object Serialization : NetworkError

        /**
         * Represents an unknown or unexpected error during network operations.
         */
        data object Unknown : NetworkError
    }

    /**
     * Represents an error when no search results are found.
     */
    data object NoSearchResult : DataError

    /**
     * Represents a general error that occurs during local data operations (e.g., database access).
     */
    data object LocalError : DataError
}