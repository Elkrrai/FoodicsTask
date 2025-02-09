package com.example.foodicstask.tables.domain

import com.example.foodicstask.core.domain.util.Error.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

interface FoodDataSource {
    suspend fun fetchCategories(): Result<List<Category>, NetworkError>
    suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError>
}
