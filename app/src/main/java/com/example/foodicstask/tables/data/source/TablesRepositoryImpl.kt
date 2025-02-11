package com.example.foodicstask.tables.data.source

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.map
import com.example.foodicstask.tables.data.source.local.LocalTablesDataSource
import com.example.foodicstask.tables.data.source.mappers.toCategory
import com.example.foodicstask.tables.data.source.mappers.toEntity
import com.example.foodicstask.tables.data.source.mappers.toProduct
import com.example.foodicstask.tables.data.source.remote.RemoteTablesDataSource
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import kotlin.collections.map

class TablesRepositoryImpl(
    private val remoteDataSource: RemoteTablesDataSource,
    private val localDataSource: LocalTablesDataSource
) : TablesRepository {
    override suspend fun fetchCategories(): Result<List<Category>, DataError.NetworkError> {
        return remoteDataSource.fetchCategories()
    }

    override suspend fun fetchProducts(categoryId: Int): Result<List<Product>, DataError.NetworkError> {
        return remoteDataSource.fetchProducts(categoryId)
    }

    override suspend fun getLocalCategories(): Result<List<Category>, DataError.LocalError> {
        return localDataSource.fetchCategories()
            .map { categories ->
                categories.map { it.toCategory() }
            }
    }

    override suspend fun getLocalProducts(categoryId: Int): Result<List<Product>, DataError.LocalError> {
        return localDataSource.fetchProducts(categoryId)
            .map { products ->
                products.map { it.toProduct() }
            }
    }

    override suspend fun insertCategories(categories: List<Category>) {
        localDataSource.insertCategories(categories.map { it.toEntity() })
    }

    override suspend fun insertProducts(products: List<Product>) {
        localDataSource.insertProducts(products.map { it.toEntity() })
    }

    override suspend fun updateProductsAndCategoryRelation(
        category: Category,
        productsIds: List<Int>
    ) {
        localDataSource.upsertCategory(
            category = category
                .toEntity()
                .copy(
                    productIds = productsIds
                )
        )
    }
}
