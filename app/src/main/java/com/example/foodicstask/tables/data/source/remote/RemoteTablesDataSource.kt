package com.example.foodicstask.tables.data.source.remote

import com.example.foodicstask.core.data.networking.Routes
import com.example.foodicstask.core.data.networking.safeCall
import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.map
import com.example.foodicstask.tables.data.source.mappers.toCategory
import com.example.foodicstask.tables.data.source.mappers.toProduct
import com.example.foodicstask.tables.data.source.remote.dto.CategoryDto
import com.example.foodicstask.tables.data.source.remote.dto.ProductDto
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * API key used for authenticating requests to the remote data source.
 *
 * **Important:** This key should be stored securely, ideally in a separate
 * configuration file or environment variable, and not directly in the source code.
 * For demonstration purposes, it's included here, but in a real application,
 * it should be used in a more secure method to manage API keys.
 */
private const val API_KEY = "1f9896f0"

/**
 * Remote data source for fetching tables-related data from a remote server.
 *
 * This class handles network requests to retrieve categories and products.
 * It uses an [HttpClient] for making the requests and the [safeCall] function
 * for handling network errors.
 *
 * @property client The [HttpClient] used for making network requests.
 */
class RemoteTablesDataSource(
    private val client: HttpClient
) {
    /**
     * Fetches a list of categories from the remote server.
     *
     * This function makes a GET request to the categories endpoint and
     * returns a [Result] containing either a list of [Category] objects on success
     * or a [NetworkError] on failure.
     *
     * @return A [Result] containing a list of [Category] objects or a [NetworkError].
     */
    suspend fun fetchCategories(): Result<List<Category>, NetworkError> {
        return safeCall<List<CategoryDto>> {
            client.get(
                urlString = Routes.getCategoriesRoute()
            ) {
                parameter("key", API_KEY)
            }
        }.map { response ->
            response.map { it.toCategory() }
        }
    }

    /**
     * Fetches a list of products for a specific category from the remote server.
     *
     * This function makes a GET request to the products endpoint for the given
     * [categoryId] and returns a [Result] containing either a list of [Product]
     * objects on success or a [NetworkError] on failure.
     *
     * @param categoryId The ID of the category for which to fetch products.
     * @return A [Result] containing a list of [Product] objects or a [NetworkError].
     */
    suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError> {
        return safeCall<List<ProductDto>> {
            client.get(
                urlString = Routes.getProductsRoute(categoryId)
            ) {
                parameter("key", API_KEY)
            }
        }.map { response ->
            response.map { it.toProduct() }
        }
    }
}
