package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCategoriesUseCaseTest {

    private val repository: TablesRepository = mockk()
    private val useCase = GetCategoriesUseCase(repository)

    private val categories = listOf(
        Category(id = 1, name = "Category 1"),
        Category(id = 2, name = "Category 2")
    )

    @Test
    fun `invoke returns remote categories when remote fetch is successful`() = runTest {
        // Arrange
        coEvery { repository.fetchCategories() } returns Result.Success(categories)
        coEvery { repository.insertCategories(any()) } returns Unit

        // Act
        val result = useCase()

        // Assert
        coVerify(exactly = 1) { repository.fetchCategories() }
        coVerify(exactly = 1) { repository.insertCategories(categories) }
        assertEquals(Result.Success(categories), result)
    }

    @Test
    fun `invoke returns local categories when remote fetch fails and local fetch is successful`() = runTest {
        // Arrange
        coEvery { repository.fetchCategories() } returns Result.Error(DataError.NetworkError.ServerError)
        coEvery { repository.getLocalCategories() } returns Result.Success(categories)

        // Act
        val result = useCase()

        // Assert
        coVerify(exactly = 1) { repository.fetchCategories() }
        coVerify(exactly = 1) { repository.getLocalCategories() }
        coVerify(exactly = 0) { repository.insertCategories(any()) }
        assertEquals(Result.Success(categories), result)
    }

    @Test
    fun `invoke returns remote error when remote fetch fails and local fetch is successful but empty`() = runTest {
        // Arrange
        val remoteError = DataError.NetworkError.RequestTimeout
        coEvery { repository.fetchCategories() } returns Result.Error(remoteError)
        coEvery { repository.getLocalCategories() } returns Result.Success(emptyList())

        // Act
        val result = useCase()

        // Assert
        coVerify(exactly = 1) { repository.fetchCategories() }
        coVerify(exactly = 1) { repository.getLocalCategories() }
        coVerify(exactly = 0) { repository.insertCategories(any()) }
        assertEquals(Result.Error(remoteError), result)
    }

    @Test
    fun `invoke returns local error when remote fetch fails and local fetch fails`() = runTest {
        // Arrange
        coEvery { repository.fetchCategories() } returns Result.Error(DataError.NetworkError.NoInternet)
        coEvery { repository.getLocalCategories() } returns Result.Error(DataError.LocalError)

        // Act
        val result = useCase()

        // Assert
        coVerify(exactly = 1) { repository.fetchCategories() }
        coVerify(exactly = 1) { repository.getLocalCategories() }
        coVerify(exactly = 0) { repository.insertCategories(any()) }
        assertEquals(Result.Error(DataError.LocalError), result)
    }
}
