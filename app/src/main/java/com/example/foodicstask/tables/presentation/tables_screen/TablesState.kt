package com.example.foodicstask.tables.presentation.tables_screen

import com.example.foodicstask.core.presentation.util.TextResource
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

data class TablesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val searchResult: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val selectedCategoryIndex: Int = 0,
    val errorMessage: TextResource? = null,
    val productsQuantity: Int = 0,
    val totalPrice: Double = 0.0
)
