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
import kotlinx.coroutines.flow.StateFlow
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

/**
 * [TablesViewModel] is responsible for managing the UI state and handling user interactions
 * related to the tables screen.
 *
 * It interacts with the domain layer through [GetCategoriesUseCase] and [GetProductsUseCase]
 * to fetch and manage data.
 *
 * @property getCategories Use case for retrieving a list of categories.
 * @property getProducts Use case for retrieving a list of products for a specific category.
 */
class TablesViewModel(
    private val getCategories: GetCategoriesUseCase,
    private val getProducts: GetProductsUseCase
) : ViewModel() {
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(TablesState())
    /**
     * The current state of the tables screen.
     *
     * It's a [StateFlow] that emits updates whenever the state changes.
     * The initial state is [TablesState].
     *
     * The state is updated in the following cases:
     * - When the categories are fetched.
     * - When a category is selected.
     * - When a product is clicked.
     * - When the order summary is clicked.
     * - When the search query is submitted.
     * - When the search result is updated.
     * - When an error occurs.
     * - When the loading state changes.
     */
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

    /**
     * A stream of events that the UI should react to.
     *
     * It's a [Channel] that emits events like [TablesEvent.ShowError].
     *
     * The events are emitted in the following cases:
     * - When an error occurs while fetching categories or products.
     * - When no search result is found.
     */
    private val _events = Channel<TablesEvent>()
    val events = _events.receiveAsFlow()

    /**
     * Handles user actions on the tables screen.
     *
     * @param action The [TablesAction] performed by the user.
     *
     * The following actions are supported:
     * - [TablesAction.OnProductClick]: When a product is clicked.
     * - [TablesAction.OnCategorySelected]: When a category is selected.
     * - [TablesAction.OnOrderSummaryClick]: When the order summary is clicked.
     * - [TablesAction.OnSearchQuerySubmit]: When the search query is submitted.
     */
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

    /**
     * Fetches the list of categories from the [getCategories] use case and updates the state.
     *
     * It also selects the first category and fetches its products.
     *
     * In case of success, it updates the state with the list of categories and sets the loading state to false.
     * In case of error, it updates the state with the loading state to false and emits a [TablesEvent.ShowError] event.
     */
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

    /**
     * Selects a category and fetches its products from the [getProducts] use case.
     *
     * @param index The index of the selected category.
     * @param category The selected category.
     *
     * In case of success, it updates the state with the list of products, the selected category index, and sets the loading state to false.
     * In case of error, it updates the state with the loading state to false and emits a [TablesEvent.ShowError] event.
     */
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

    /**
     * Handles the click on a product.
     *
     * @param product The clicked product.
     *
     * It updates the state with the updated product list, the total price, and the number of ordered products.
     */
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

    /**
     * Handles the click on the order summary.
     *
     * It updates the state with the updated product list, resets the total price, and the number of ordered products.
     */
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

    /**
     * Observes changes in the [state]'s `searchQuery` property and initiates a search when the query changes.
     *
     * This function uses a debounce of 1000ms to avoid performing a search on every keystroke.
     * It also ensures that only distinct queries are processed.
     *
     * - If the query is blank, it clears the `searchResult` in the state.
     * - If the query has at least 2 characters, it cancels any previous search job and starts a new one by calling [searchBooks].
     *
     * This function is called automatically when the [state] flow starts collecting.
     *
     * @see searchBooks
     */
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(1000L)
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

    /**
     * Searches for products whose names contain the given [query].
     *
     * This function filters the current list of products in the [state] and updates the `searchResult` in the state with the matching products.
     *
     * - It sets the `isLoading` state to `true` while the search is in progress.
     * - It performs the search on the [Dispatchers.Default] dispatcher to avoid blocking the main thread.
     * - If no products match the query, it emits a [TablesEvent.ShowError] event with [NoSearchResult].
     * - It sets the `isLoading` state to `false` after the search is complete.
     *
     * @param query The search query.
     */
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
