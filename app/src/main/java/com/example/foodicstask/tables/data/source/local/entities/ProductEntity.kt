package com.example.foodicstask.tables.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
)
