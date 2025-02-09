package com.example.foodicstask.tables.data.source.remote

import com.example.foodicstask.core.data.networking.Routes
import com.example.foodicstask.core.data.networking.safeCall
import com.example.foodicstask.core.domain.util.Error.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.map
import com.example.foodicstask.tables.data.source.mappers.toCategory
import com.example.foodicstask.tables.data.source.mappers.toProduct
import com.example.foodicstask.tables.data.source.remote.dto.CategoryDto
import com.example.foodicstask.tables.data.source.remote.dto.ProductDto
import com.example.foodicstask.tables.domain.FoodDataSource
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteFoodDataSource(
    private val client: HttpClient
) : FoodDataSource {

    override suspend fun fetchCategories(): Result<List<Category>, NetworkError> {
        return safeCall<List<CategoryDto>> {
            client.get(
                urlString = Routes.getCategoriesRoute()
            )
        }.map { response ->
            response.map { it.toCategory() }
        }
    }

    override suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError> {
        return safeCall<List<ProductDto>> {
            client.get(
                urlString = Routes.getProductsRoute(categoryId)
            )
        }.map { response ->
            response.map { it.toProduct() }
        }
    }
}
