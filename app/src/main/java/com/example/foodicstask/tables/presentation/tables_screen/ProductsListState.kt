package com.example.foodicstask.tables.presentation.tables_screen

import com.example.foodicstask.tables.domain.entities.Product

data class ProductsListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val searchResult: List<Product> = emptyList(),
    val products: List<Product> = emptyList(),
)
