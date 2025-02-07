package com.example.foodicstask.food.domain

import com.example.foodicstask.core.domain.util.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.food.domain.entities.Category
import com.example.foodicstask.food.domain.entities.Product

interface FoodDataSource {
    suspend fun fetchCategories(): Result<List<Category>, NetworkError>
    suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError>
}
