package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.Error.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.tables.domain.FoodDataSource
import com.example.foodicstask.tables.domain.entities.Product

class FetchProductsUseCase(
    private val foodDataSource: FoodDataSource
) {
    suspend operator fun invoke(categoryId: Int): Result<List<Product>, NetworkError> {
        return foodDataSource
            .fetchProducts(categoryId)
            .onError {
                // TODO: get products from local db
                return Result.Error(it)
            }
    }
}
