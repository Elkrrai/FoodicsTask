package com.example.foodicstask.tables.data.source.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity

@Dao
interface CategoriesDao {
    @Upsert
    suspend fun upsert(category : CategoryEntity)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAll(): List<CategoryEntity>
}
