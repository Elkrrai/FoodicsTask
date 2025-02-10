package com.example.foodicstask.tables.data.source.local.database

import com.example.foodicstask.tables.data.source.local.daos.CategoriesDao

interface CategoriesDatabase {
    fun categoriesDao(): CategoriesDao
}
