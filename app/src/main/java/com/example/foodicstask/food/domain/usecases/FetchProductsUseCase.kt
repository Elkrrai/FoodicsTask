package com.example.foodicstask.food.domain.usecases

import com.example.foodicstask.core.domain.util.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.food.domain.FoodDataSource
import com.example.foodicstask.food.domain.entities.Product

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
