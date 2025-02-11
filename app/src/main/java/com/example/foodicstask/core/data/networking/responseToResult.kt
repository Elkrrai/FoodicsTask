package com.example.foodicstask.core.data.networking

import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * Converts an [HttpResponse] to a [Result].
 *
 * This function handles common HTTP status codes and maps them to appropriate [Result] states.
 *
 * @param response The [HttpResponse] to convert.
 * @return A [Result] containing the parsed response body on success, or a [NetworkError] on failure.
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(NetworkError.Serialization)
            }
        }
        408 -> Result.Error(NetworkError.RequestTimeout)
        429 -> Result.Error(NetworkError.TooManyRequests)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> Result.Error(NetworkError.Unknown)
    }
}
