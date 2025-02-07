package com.example.foodicstask.food.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String,
)
