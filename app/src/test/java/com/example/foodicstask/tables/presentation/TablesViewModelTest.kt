package com.example.foodicstask.tables.presentation

import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.usecases.GetCategoriesUseCase
import com.example.foodicstask.tables.domain.usecases.GetProductsUseCase
import com.example.foodicstask.tables.presentation.models.CategoryUi
import com.example.foodicstask.tables.presentation.models.ProductUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TablesViewModelTest {

    private lateinit var getCategories: GetCategoriesUseCase
    private lateinit var getProducts: GetProductsUseCase
    private lateinit var viewModel: TablesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCategories = mockk()
        getProducts = mockk()
        viewModel = TablesViewModel(getCategories, getProducts)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCategories() when remote fetch is successful`() = runTest {
        // Arrange
        val categories = listOf(
            Category(id = 1, name = "Category 1"),
            Category(id = 2, name = "Category 2")
        )
        coEvery { getCategories.invoke() } returns Result.Success(categories)
        coEvery { getProducts.invoke(any()) } returns Result.Success(emptyList())

        // Assert
        val emittedStates = mutableListOf<TablesState>()
        val collectJob = launch {
            viewModel.state.collect {
                emittedStates.add(it)
            }
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(emittedStates[0].isLoading)

        assertEquals(
            categories.map { CategoryUi(it.id, it.name) },
            emittedStates[1].categories
        )

        coVerify { getProducts.invoke(categories.first()) }

        collectJob.cancel()
    }

    @Test
    fun `observeSearchQuery() when query is blank then searchResult is cleared`() = runTest {
        // Arrange
        val categories = listOf(
            Category(id = 1, name = "Category 1"),
            Category(id = 2, name = "Category 2")
        )
        coEvery { getCategories.invoke() } returns Result.Success(categories)
        coEvery { getProducts.invoke(any()) } returns Result.Success(emptyList())

        val emittedStates = mutableListOf<TablesState>()
        val collectJob = launch {
            viewModel.state.collect {
                emittedStates.add(it)
            }
        }

        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.onAction(TablesAction.OnSearchQuerySubmit(""))
        advanceTimeBy(1000L)

        // Assert
        assertEquals(emptyList<ProductUi>(), emittedStates.last().searchResult)

        collectJob.cancel()
    }

    @Test
    fun `observeSearchQuery() when query is less than 2 characters then searchBooks is not called`() =
        runTest {
            // Arrange
            val categories = listOf(
                Category(id = 1, name = "Category 1"),
                Category(id = 2, name = "Category 2")
            )
            coEvery { getCategories.invoke() } returns Result.Success(categories)
            coEvery { getProducts.invoke(any()) } returns Result.Success(emptyList())

            val emittedStates = mutableListOf<TablesState>()
            val collectJob = launch {
                viewModel.state.collect {
                    emittedStates.add(it)
                }
            }

            testDispatcher.scheduler.advanceUntilIdle()
            val initialState = emittedStates.last()

            // Act
            viewModel.onAction(TablesAction.OnSearchQuerySubmit("a"))
            advanceTimeBy(1000L)

            // Assert
            assertEquals(initialState.searchResult, emittedStates.last().searchResult)
            assertEquals(initialState.isLoading, emittedStates.last().isLoading)

            collectJob.cancel()
        }
}
