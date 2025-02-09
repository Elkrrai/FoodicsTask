package com.example.foodicstask.tables.presentation.models

data class ProductUi(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val category: CategoryUi,
    val ordered: Int,
)
