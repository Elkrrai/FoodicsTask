package com.example.foodicstask.tables.data.source.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity

/**
 * Data Access Object (DAO) for [ProductEntity].
 *
 * This interface provides methods for interacting with the product data stored in the Room database.
 */
@Dao
interface ProductsDao {
    /**
     * Inserts or updates a [ProductEntity] in the database.
     *
     * If a product with the same primary key already exists, it will be updated.
     * Otherwise, a new product will be inserted.
     *
     * @param product The [ProductEntity] to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(product: ProductEntity)

    /**
     * Inserts a list of [ProductEntity] objects into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<ProductEntity>)

    /**
     * Retrieves a [ProductEntity] from the database by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The [ProductEntity] with the specified ID.
     */
    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun getProduct(id: Int): ProductEntity
}