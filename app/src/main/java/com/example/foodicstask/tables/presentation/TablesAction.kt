package com.example.foodicstask.tables.presentation

import com.example.foodicstask.tables.domain.entities.Product

sealed interface TablesAction {
    data class OnSearchQuerySubmit(val query: String) : TablesAction
    object FetchCategories : TablesAction
    data class FetchProducts(val categoryId: Int) : TablesAction
    data class OnCategorySelected(val index: Int) : TablesAction
    data class OnProductClick(val product: Product) : TablesAction
}
