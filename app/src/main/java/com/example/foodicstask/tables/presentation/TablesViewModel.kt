package com.example.foodicstask.tables.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.core.domain.util.onSuccess
import com.example.foodicstask.tables.domain.usecases.FetchCategoriesUseCase
import com.example.foodicstask.tables.domain.usecases.FetchProductsUseCase
import com.example.foodicstask.tables.presentation.mappers.toUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TablesViewModel(
    private val getCategories: FetchCategoriesUseCase,
    private val getProducts: FetchProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TablesState())
    val state = _state
        .onStart { fetchCategories() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000L),
            TablesState()
        )

    private val _events = Channel<TablesEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TablesAction) {
        when (action) {
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

    private fun fetchCategories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            getCategories.invoke()
                .onSuccess { categories ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = categories.map { it.toUiModel() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(TablesEvent.Error(error))
                }
        }
    }
}
