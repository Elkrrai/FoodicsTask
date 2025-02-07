package com.example.foodicstask.tables.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String,
)
