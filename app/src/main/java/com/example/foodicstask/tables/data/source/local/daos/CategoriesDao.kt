package com.example.foodicstask.tables.data.source.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity

/**
 * Data Access Object (DAO) for [CategoryEntity].
 *
 * This interface provides methods for interacting with the category data stored in the Room database.
 */
@Dao
interface CategoriesDao {
    /**
     * Inserts or updates a [CategoryEntity] in the database.
     *
     * If a category with the same primary key already exists, it will be updated.
     * Otherwise, a new category will be inserted.
     *
     * @param category The [CategoryEntity] to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(category: CategoryEntity)

    /**
     * Inserts a list of [CategoryEntity] objects into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    /**
     * Retrieves all [CategoryEntity] objects from the database.
     *
     * @return A list of all [CategoryEntity] objects in the database.
     */
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAll(): List<CategoryEntity>

    /**
     * Retrieves a [CategoryEntity] from the database by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return The [CategoryEntity] with the specified ID.
     */
    @Query("SELECT * FROM CategoryEntity WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryEntity
}
