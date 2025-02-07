package com.example.foodicstask.food.domain.usecases

import com.example.foodicstask.core.domain.util.NetworkError
import com.example.foodicstask.food.domain.FoodDataSource
import com.example.foodicstask.food.domain.entities.Category
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.onError

class FetchCategoriesUseCase(
    private val foodDataSource: FoodDataSource
) {
    suspend operator fun invoke(): Result<List<Category>, NetworkError> {
        return foodDataSource
            .fetchCategories()
            .onError {
                // TODO: get categories from local db
                return Result.Error(it)
            }
    }
}
