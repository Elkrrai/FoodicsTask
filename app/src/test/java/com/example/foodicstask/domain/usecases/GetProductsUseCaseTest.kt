package com.example.foodicstask.domain.usecases

import com.example.foodicstask.tables.domain.usecases.GetProductsUseCase
import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest {

    private val repository: TablesRepository = mockk()
    private val useCase = GetProductsUseCase(repository)

    private val category = Category(id = 1, name = "Category 1")
    private val products = listOf(
        Product(
            id = 1,
            name = "Product 1",
            image = "url1",
            price = 10.0,
            category = category,
            description = "description 1",
        ),
        Product(
            id = 2,
            name = "Product 2",
            image = "url2",
            price = 20.0,
            category = category,
            description = "description 2",
        )
    )

    @Test
    fun `invoke returns remote products when remote fetch is successful`() = runTest {
        // Arrange
        coEvery { repository.fetchProducts(category.id) } returns Result.Success(products)
        coEvery { repository.insertProducts(any()) } returns Unit
        coEvery { repository.updateProductsAndCategoryRelation(any(), any()) } returns Unit

        // Act
        val result = useCase(category)

        // Assert
        coVerify(exactly = 1) { repository.fetchProducts(category.id) }
        coVerify(exactly = 1) { repository.insertProducts(products) }
        coVerify(exactly = 1) {
            repository.updateProductsAndCategoryRelation(
                category,
                listOf(1, 2)
            )
        }
        assertEquals(Result.Success(products), result)
    }

    @Test
    fun `invoke returns local products when remote fetch fails and local fetch is successful`() =
        runTest {
            // Arrange
            coEvery { repository.fetchProducts(category.id) } returns Result.Error(DataError.NetworkError.ServerError)
            coEvery { repository.getLocalProducts(category) } returns Result.Success(products)

            // Act
            val result = useCase(category)

            // Assert
            coVerify(exactly = 1) { repository.fetchProducts(category.id) }
            coVerify(exactly = 1) { repository.getLocalProducts(category) }
            coVerify(exactly = 0) { repository.insertProducts(any()) }
            coVerify(exactly = 0) { repository.updateProductsAndCategoryRelation(any(), any()) }
            assertEquals(Result.Success(products), result)
        }

    @Test
    fun `invoke returns remote error when remote fetch fails and local fetch is successful but empty`() =
        runTest {
            // Arrange
            val remoteError = DataError.NetworkError.RequestTimeout
            coEvery { repository.fetchProducts(category.id) } returns Result.Error(remoteError)
            coEvery { repository.getLocalProducts(category) } returns Result.Success(emptyList())

            // Act
            val result = useCase(category)

            // Assert
            coVerify(exactly = 1) { repository.fetchProducts(category.id) }
            coVerify(exactly = 1) { repository.getLocalProducts(category) }
            coVerify(exactly = 0) { repository.insertProducts(any()) }
            coVerify(exactly = 0) { repository.updateProductsAndCategoryRelation(any(), any()) }
            assertEquals(Result.Error(remoteError), result)
        }

    @Test
    fun `invoke returns local error when remote fetch fails and local fetch fails`() = runTest {
        // Arrange
        coEvery { repository.fetchProducts(category.id) } returns Result.Error(DataError.NetworkError.NoInternet)
        coEvery { repository.getLocalProducts(category) } returns Result.Error(DataError.LocalError)

        // Act
        val result = useCase(category)

        // Assert
        coVerify(exactly = 1) { repository.fetchProducts(category.id) }
        coVerify(exactly = 1) { repository.getLocalProducts(category) }
        coVerify(exactly = 0) { repository.insertProducts(any()) }
        coVerify(exactly = 0) { repository.updateProductsAndCategoryRelation(any(), any()) }
        assertEquals(Result.Error(DataError.LocalError), result)
    }

    @Test
    fun `invoke does not update products and category relation when productsIds is empty`() =
        runTest {
            // Arrange
            coEvery { repository.fetchProducts(category.id) } returns Result.Success(emptyList())
            coEvery { repository.insertProducts(emptyList()) } returns Unit

            // Act
            val result = useCase(category)

            // Assert
            coVerify(exactly = 1) { repository.fetchProducts(category.id) }
            coVerify(exactly = 1) { repository.insertProducts(emptyList()) }
            coVerify(exactly = 0) { repository.updateProductsAndCategoryRelation(any(), any()) }
            assertEquals(Result.Success(emptyList<Product>()), result)
        }
}
