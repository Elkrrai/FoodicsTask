package com.example.foodicstask.tables.presentation

import com.example.foodicstask.tables.presentation.models.ProductUi

sealed interface TablesAction {
    data class OnSearchQuerySubmit(val query: String) : TablesAction
    data class OnCategorySelected(val index: Int, val categoryId: Int) : TablesAction
    data class OnProductClick(val product: ProductUi) : TablesAction
    data object OnOrderSummaryClick : TablesAction
}
