package com.example.foodicstask.core.data.networking

import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

/**
 * Executes a network call safely, handling common exceptions and converting the response to a [Result].
 *
 * This function wraps a network call to handle potential exceptions
 * such as network connectivity issues, serialization errors, and other unexpected exceptions.
 * It then converts the [HttpResponse] to a [Result].
 *
 * @param execute A lambda function that performs the network call and returns an [HttpResponse].
 * @return A [Result] containing the parsed response body on success, or a [NetworkError] on failure.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NoInternet)
    } catch(e: SerializationException) {
        return Result.Error(NetworkError.Serialization)
    } catch(e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.Unknown)
    }

    return responseToResult(response)
}
