package com.example.foodicstask.tables.data.source.local.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foodicstask.tables.data.source.local.database.TablesDatabase
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    private lateinit var database: TablesDatabase
    private lateinit var categoryDao: CategoriesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, TablesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoriesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCategoryAndRetrieveIt() = runBlocking {
        // Arrange: Create a sample category
        val category = CategoryEntity(
            id = 1,
            name = "Breakfast",
            productIds = listOf(101, 102, 103)
        )

        // Act: Insert category into DB
        categoryDao.upsert(category)

        // Assert: Retrieve category and validate data
        val retrievedCategory = categoryDao.getCategoryById(1)

        assertNotNull(retrievedCategory)
        assertEquals(category.id, retrievedCategory.id)
        assertEquals(category.name, retrievedCategory.name)
        assertEquals(category.productIds, retrievedCategory.productIds)
    }

    @Test
    fun insertAllCategoriesAndRetrieveThem() = runBlocking {
        // Arrange: Create a list of categories
        val categories = listOf(
            CategoryEntity(id = 1, name = "Category 1", productIds = listOf(101, 102)),
            CategoryEntity(id = 2, name = "Category 2", productIds = listOf(201, 202)),
            CategoryEntity(id = 3, name = "Category 3", productIds = listOf(301, 302))
        )

        // Act: Insert all categories into the database
        categoryDao.insertAll(categories)

        // Assert: Retrieve categories and validate data
        val retrievedCategories = categoryDao.getAll()

        assertEquals(3, retrievedCategories.size)
        assertEquals(categories[0], retrievedCategories[0])
        assertEquals(categories[1], retrievedCategories[1])
        assertEquals(categories[2], retrievedCategories[2])
    }
}
