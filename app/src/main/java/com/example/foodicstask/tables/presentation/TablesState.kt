package com.example.foodicstask.tables.presentation

import com.example.foodicstask.tables.presentation.models.CategoryUi
import com.example.foodicstask.tables.presentation.models.ProductUi

data class TablesState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResult: List<ProductUi> = emptyList(),
    val categories: List<CategoryUi> = emptyList(),
    val products: List<ProductUi> = emptyList(),
    val selectedCategoryIndex: Int = 0,
    val orderedProducts: Int = 0,
    val totalPrice: Double = 0.0
)
