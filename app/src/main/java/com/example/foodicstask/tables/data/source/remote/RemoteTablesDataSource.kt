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

private const val API_KEY = "1f9896f0"  // this should be hidden in a secret file

class RemoteTablesDataSource(
    private val client: HttpClient
) {
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
