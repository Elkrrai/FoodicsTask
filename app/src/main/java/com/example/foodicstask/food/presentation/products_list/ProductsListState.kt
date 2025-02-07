package com.example.foodicstask.food.presentation.products_list

import com.example.foodicstask.food.domain.entities.Product

data class ProductsListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val searchResult: List<Product> = emptyList(),
    val products: List<Product> = emptyList(),
)
