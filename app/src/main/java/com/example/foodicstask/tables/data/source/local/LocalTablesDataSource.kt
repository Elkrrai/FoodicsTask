package com.example.foodicstask.tables.data.source.local

import com.example.foodicstask.core.domain.util.DataError.LocalError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.data.source.local.database.TablesDatabase
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity
import java.sql.SQLException

/**
 * [LocalTablesDataSource] is a data source class responsible for managing local data operations
 * related to categories and products. It interacts with the [TablesDatabase] to perform
 * CRUD (Create, Read, Update, Delete) operations on the local database.
 *
 * This class provides methods to:
 * - Fetch all categories.
 * - Insert or update a single category.
 * - Insert multiple categories.
 * - Fetch products associated with a specific category.
 * - Insert multiple products.
 *
 * @property database The [TablesDatabase] instance used for database interactions.
 */
class LocalTablesDataSource(
    private val database: TablesDatabase
) {
    /**
     * Fetches all categories from the local database.
     *
     * @return A [Result] object containing either a list of [CategoryEntity] on success
     *         or a [LocalError] on failure.
     */
    suspend fun fetchCategories(): Result<List<CategoryEntity>, LocalError> {
        return try {
            val result = database.categoriesDao().getAll()
            Result.Success(result)
        } catch (e: SQLException) {
            Result.Error(LocalError)
        }
    }

    /**
     * Inserts or updates a single category in the local database.
     *
     * @param category The [CategoryEntity] to be inserted or updated.
     */
    suspend fun upsertCategory(category: CategoryEntity) {
        database.categoriesDao().upsert(category)
    }

    /**
     * Inserts multiple categories into the local database.
     *
     * @param categories A list of [CategoryEntity] to be inserted.
     */
    suspend fun insertCategories(categories: List<CategoryEntity>) {
        database.categoriesDao().insertAll(categories)
    }

    /**
     * Fetches products associated with a specific category from the local database.
     *
     * @param categoryId The ID of the category for which to fetch products.
     * @return A [Result] object containing either a list of [ProductEntity] on success
     *         or a [LocalError] on failure.
     */
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

    /**
     * Retrieves products by their IDs from the local database.
     *
     * @param productIds A list of product IDs to retrieve.
     * @return A list of [ProductEntity] corresponding to the provided IDs.
     */
    private suspend fun getProductsByIds(productIds: List<Int>): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        for (productId in productIds) {
            val product = database.productsDao().getProduct(productId)
            products.add(product)
        }
        return products
    }

    /**
     * Inserts multiple products into the local database.
     *
     * @param products A list of [ProductEntity] to be inserted.
     */
    suspend fun insertProducts(products: List<ProductEntity>) {
        database.productsDao().insertAll(products)
    }
}
