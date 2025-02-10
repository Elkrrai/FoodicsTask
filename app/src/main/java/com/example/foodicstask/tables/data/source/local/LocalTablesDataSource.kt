package com.example.foodicstask.tables.data.source.local

import android.util.Log
import com.example.foodicstask.core.domain.util.DataError.LocalError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.data.source.local.database.TablesDatabase
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity
import java.sql.SQLException

class LocalTablesDataSource(
    private val database: TablesDatabase
) {
    suspend fun fetchCategories(): Result<List<CategoryEntity>, LocalError> {
        return try {
            val result = database.categoriesDao().getAll()
            Result.Success(result)
        } catch (e: SQLException) {
            Result.Error(LocalError)
        }
    }

    suspend fun upsertCategory(category: CategoryEntity) {
        database.categoriesDao().upsert(category)
    }

    suspend fun insertCategories(categories: List<CategoryEntity>) {
        categories.forEach { category ->
            upsertCategory(category)
        }
    }

    suspend fun fetchProducts(categoryId: Int): Result<List<ProductEntity>, LocalError> {
        return try {
            val category = database.categoriesDao().getCategoryById(categoryId)
            if (category.productIds.isEmpty()) {
                return Result.Error(LocalError)
            }
            val productIds = category.productIds
            val result = getProductsByIds(productIds)
            Result.Success(result)
        } catch (e: SQLException) {
            Result.Error(LocalError)
        }
    }

    private suspend fun getProductsByIds(productIds: List<Int>): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        for (productId in productIds) {
            val product = database.productsDao().getProduct(productId)
            products.add(product)
        }
        return products
    }

    suspend fun upsertProduct(product: ProductEntity) {
        database.productsDao().upsert(product)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        products.forEach { product ->
            upsertProduct(product)
        }
    }
}
