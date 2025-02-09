package com.example.foodicstask.tables.domain.entities

data class Product(
    val id: Int,
    val category: Category,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val ordered :Int
)
