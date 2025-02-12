package com.example.foodicstask.tables.data.source.local.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foodicstask.tables.data.source.local.database.TablesDatabase
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductsDaoTest {

    private lateinit var database: TablesDatabase
    private lateinit var productsDao: ProductsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, TablesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        productsDao = database.productsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun givenProductInserted_whenRetrievedById_thenReturnsCorrectProduct() = runBlocking {
        // Arrange: Create a product entity
        val product = ProductEntity(
            id = 1,
            name = "Product 1",
            categoryId = 1,
            price = 999.99,
            image = "image1",
            description = "des1"
        )

        // Act: Insert product and retrieve it
        productsDao.upsert(product)
        val retrievedProduct = productsDao.getProduct(1)

        // Assert: Check if the retrieved product matches the inserted one
        assertNotNull(retrievedProduct)
        assertEquals(product.id, retrievedProduct.id)
        assertEquals(product.name, retrievedProduct.name)
        assertEquals(product.categoryId, retrievedProduct.categoryId)
        assertEquals(product.price, retrievedProduct.price, 0.001)
    }

    @Test
    fun givenMultipleProductsInserted_whenGetAll_thenReturnsCorrectList() = runBlocking {
        // Arrange: Create a list of products
        val products = listOf(
            ProductEntity(
                id = 1,
                name = "Product 1",
                categoryId = 1,
                price = 999.99,
                image = "image1",
                description = "des1"
            ),
            ProductEntity(
                id = 2,
                name = "Product 2",
                categoryId = 2,
                price = 999.99,
                image = "image2",
                description = "des2"
            ),
            ProductEntity(
                id = 3,
                name = "Product 3",
                categoryId = 2,
                price = 999.99,
                image = "image3",
                description = "ds3"
            ),
        )

        // Act: Insert all products
        productsDao.insertAll(products)

        // Assert: Retrieve and check all products
        val retrievedProduct1 = productsDao.getProduct(1)
        val retrievedProduct2 = productsDao.getProduct(2)
        val retrievedProduct3 = productsDao.getProduct(3)

        assertEquals(products[0].id, retrievedProduct1.id)
        assertEquals(products[1].id, retrievedProduct2.id)
        assertEquals(products[2].id, retrievedProduct3.id)
    }
}
