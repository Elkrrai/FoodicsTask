package com.example.foodicstask.tables.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TablesViewModel : ViewModel() {
    private val _state = MutableStateFlow(TablesState())
    val state = _state.asStateFlow()

    fun onAction(action: TablesAction) {
        when (action) {
            is TablesAction.FetchCategories -> TODO()
            is TablesAction.FetchProducts -> TODO()
            is TablesAction.OnProductClick -> {
                _state.update {
                    it.copy(
                        productsQuantity = it.productsQuantity + 1,
                        totalPrice = it.totalPrice + action.product.price
                    )
                }
            }

            is TablesAction.OnCategorySelected -> {
                _state.update {
                    it.copy(
                        selectedCategoryIndex = action.index
                    )
                }
            }

            is TablesAction.OnSearchQuerySubmit -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query
                    )
                }
            }
        }
    }
}
