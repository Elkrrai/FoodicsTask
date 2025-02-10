package com.example.foodicstask.tables.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodicstask.core.domain.util.DataError.NoSearchResult
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.core.domain.util.onSuccess
import com.example.foodicstask.core.presentation.util.formatToTwoDecimalPlaces
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.usecases.GetCategoriesUseCase
import com.example.foodicstask.tables.domain.usecases.GetProductsUseCase
import com.example.foodicstask.tables.presentation.mappers.toUiModel
import com.example.foodicstask.tables.presentation.models.ProductUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TablesViewModel(
    private val getCategories: GetCategoriesUseCase,
    private val getProducts: GetProductsUseCase
) : ViewModel() {
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(TablesState())
    val state = _state
        .onStart {
            fetchCategories()
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000L),
            TablesState()
        )

    private val _events = Channel<TablesEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TablesAction) {
        when (action) {
            is TablesAction.OnProductClick -> onProductClick(action.product)

            is TablesAction.OnCategorySelected -> selectCategory(action.index, action.category)

            is TablesAction.OnOrderSummaryClick -> onOrderSummaryClick()

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

                    selectCategory(0, categories.first())
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(TablesEvent.ShowError(error))
                }
        }
    }

    private fun selectCategory(index: Int, category: Category) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            getProducts.invoke(category)
                .onSuccess { products ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            selectedCategoryIndex = index,
                            products = products.map { it.toUiModel() }
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(TablesEvent.ShowError(error))
                }
        }
    }

    private fun onProductClick(product: ProductUi) {
        _state.update { state ->
            val productIndex = state.products.indexOfFirst { it.id == product.id }
            if (productIndex == -1) {
                return@update state
            }

            val updatedProducts = state.products
                .mapIndexed { index, product ->
                    if (index == productIndex) {
                        product.copy(ordered = product.ordered + 1)
                    } else {
                        product
                    }
                }

            val totalPrice = state.totalPrice + product.price
            state.copy(
                orderedProducts = state.orderedProducts + 1,
                totalPrice = totalPrice.formatToTwoDecimalPlaces(),
                products = updatedProducts
            )
        }
    }

    private fun onOrderSummaryClick() {
        _state.update { state ->
            val updatedProducts = state.products
                .map { product -> product.copy(ordered = 0) }

            state.copy(
                orderedProducts = 0,
                totalPrice = 0.0,
                products = updatedProducts
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResult = emptyList()
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { state ->
            state.copy(
                isLoading = true,
            )
        }
        val searchResult = withContext(Dispatchers.Default) {
            state.value.products.filter { product ->
                product.name.contains(query, ignoreCase = true)
            }
        }

        if (searchResult.isEmpty())
            _events.send(TablesEvent.ShowError(NoSearchResult))

        _state.update { state ->
            state.copy(
                searchResult = searchResult,
                isLoading = false
            )
        }
    }
}
