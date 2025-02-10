package com.example.foodicstask.tables.domain

import com.example.foodicstask.core.domain.util.DataError.LocalError
import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

interface TablesRepository {
    suspend fun fetchCategories(): Result<List<Category>, NetworkError>
    suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError>
    suspend fun getLocalCategories(): Result<List<Category>, LocalError>
    suspend fun getLocalProducts(categoryId:Int): Result<List<Product>, LocalError>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun insertProducts(products: List<Product>)
    suspend fun upsertProductsAndCategoryRelation(category: Category, productsIds: List<Int>)
}
