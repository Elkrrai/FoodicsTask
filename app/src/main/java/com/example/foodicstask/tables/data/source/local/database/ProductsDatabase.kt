package com.example.foodicstask.tables.data.source.local.database

import com.example.foodicstask.tables.data.source.local.daos.ProductsDao

interface ProductsDatabase {
    fun productsDao(): ProductsDao
}
