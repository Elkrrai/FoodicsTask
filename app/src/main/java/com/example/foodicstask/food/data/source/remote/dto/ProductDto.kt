package com.example.foodicstask.food.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val category: CategoryDto,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
)
