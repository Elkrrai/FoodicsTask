package com.example.foodicstask.core.domain.util

/**
 * Represents the result of an operation that can either be a success with a value or a failure with an error.
 *
 * This sealed interface is used to encapsulate the outcome of an operation, providing a type-safe way
 * to handle both successful and failed results.
 *
 * @param D The type of the data returned on success.
 * @param E The type of the error returned on failure, must be a subclass of [DataError].
 */
sealed interface Result<out D, out E : DataError> {
    /**
     * Represents a successful result with a value.
     *
     * @param data The data returned by the successful operation.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result with an error.
     *
     * @param error The error that occurred during the operation.
     */
    data class Error<out E : DataError>(val error: E) : Result<Nothing, E>
}

/**
 * Maps the data of a successful [Result] to a new value.
 *
 * If the [Result] is a [Result.Success], the provided [map] function is applied to the data,
 * and a new [Result.Success] is returned with the mapped value. If the [Result] is a [Result.Error],
 * the error is propagated unchanged.
 *
 * @param map The function to apply to the data if the result is a success.
 * @return A new [Result] with the mapped data or the original error.
 */
inline fun <T, E : DataError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Performs an action if the [Result] is a success.
 *
 * If the [Result] is a [Result.Success], the provided [action] function is executed with the data.
 * If the [Result] is a [Result.Error], no action is performed.
 *
 * @param action The function to execute with the data if the result is a success.
 * @return The original [Result].
 */
inline fun <T, E : DataError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
/**
 * Performs an action if the [Result] is an error.
 *
 * If the [Result] is a [Result.Error], the provided [action] function is executed with the error.
 * If the [Result] is a [Result.Success], no action is performed.
 *
 * @param action The function to execute with the error if the result is an error.
 * @return The original [Result].
 */
inline fun <T, E : DataError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}
