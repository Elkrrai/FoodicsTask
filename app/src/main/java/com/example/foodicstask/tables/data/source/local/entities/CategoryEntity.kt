package com.example.foodicstask.tables.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val productIds: List<Int>
)
