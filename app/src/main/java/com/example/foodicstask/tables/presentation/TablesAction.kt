package com.example.foodicstask.tables.presentation

import com.example.foodicstask.tables.presentation.models.ProductUi

sealed interface TablesAction {
    data class OnSearchQuerySubmit(val query: String) : TablesAction
    data class FetchProducts(val categoryId: Int) : TablesAction
    data class OnCategorySelected(val index: Int) : TablesAction
    data class OnProductClick(val product: ProductUi) : TablesAction
}
