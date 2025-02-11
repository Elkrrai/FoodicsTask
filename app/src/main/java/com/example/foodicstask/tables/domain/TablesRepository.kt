package com.example.foodicstask.tables.domain

import com.example.foodicstask.core.domain.util.DataError.LocalError
import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

/**
 * Interface defining the contract for interacting with tables-related data.
 * This repository provides methods to fetch, retrieve, and manage categories and products,
 * both from remote and local data sources.
 */
interface TablesRepository {
    /**
     * Fetches a list of categories from the network.
     *
     * @return A [Result] containing a list of [Category] on success, or a [NetworkError] on failure.
     */
    suspend fun fetchCategories(): Result<List<Category>, NetworkError>

    /**
     * Fetches a list of products from the network for a given category ID.
     *
     * @param categoryId The ID of the category for which to fetch products.
     * @return A [Result] containing a list of [Product] on success, or a [NetworkError] on failure.
     */
    suspend fun fetchProducts(categoryId: Int): Result<List<Product>, NetworkError>

    /**
     * Retrieves a list of categories from the local data source.
     *
     * @return A [Result] containing a list of [Category] on success, or a [LocalError] on failure.
     */
    suspend fun getLocalCategories(): Result<List<Category>, LocalError>

    /**
     * Retrieves a list of products from the local data source for a given category ID.
     *
     * @param category The category for which to retrieve products.
     * @return A [Result] containing a list of [Product] on success, or a [LocalError] on failure.
     */
    suspend fun getLocalProducts(category: Category): Result<List<Product>, LocalError>

    /**
     * Inserts a list of categories into the local data source.
     *
     * @param categories The list of [Category] to insert.
     */
    suspend fun insertCategories(categories: List<Category>)

    /**
     * Inserts a list of products into the local data source.
     *
     * @param products The list of [Product] to insert.
     */
    suspend fun insertProducts(products: List<Product>)

    /**
     * Update (updates or inserts) the relationship between a category and a list of product IDs in the local data source.
     *
     * @param category The [Category] to relate to the products.
     * @param productsIds The list of product IDs to associate with the category.
     */
    suspend fun updateProductsAndCategoryRelation(category: Category, productsIds: List<Int>)
}
