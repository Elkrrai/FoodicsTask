package com.example.foodicstask.core.data.networking

import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

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
