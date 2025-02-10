package com.example.foodicstask.tables.data.source.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity

@Dao
interface ProductsDao {
    @Upsert
    suspend fun upsert(product: ProductEntity)

    @Query("SELECT * FROM ProductEntity WHERE id IN (:productIds)")
    suspend fun getProductsByIds(productIds: List<Int>): List<ProductEntity>
}
