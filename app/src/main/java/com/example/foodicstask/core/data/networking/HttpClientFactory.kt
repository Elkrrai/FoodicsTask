package com.example.foodicstask.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * A factory object responsible for creating and configuring [HttpClient] instances.
 * It provides a centralized way to set up common configurations for HTTP clients,
 * such as logging, content negotiation, and default request settings.
 */
object HttpClientFactory {

    /**
     * Creates and configures an [HttpClient] instance with the specified [HttpClientEngine].
     *
     * This function sets up the following configurations:
     * - **Logging:** Enables logging for all HTTP requests and responses with a log level of [LogLevel.ALL]
     *   and uses the [ANDROID] logger.
     * - **Content Negotiation:** Configures the client to handle JSON content using the kotlinx.serialization library.
     *   It ignores unknown keys in the JSON response.
     * - **Default Request:** Sets the default content type for all requests to [ContentType.Application.Json].
     *
     * @param engine The [HttpClientEngine] to use for the HTTP client. This determines the underlying
     *               platform-specific implementation for making network requests.
     * @return A fully configured [HttpClient] instance.
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
