package com.example.foodicstask.tables.data.source.mappers

import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name
)

fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name
)

fun Product.toEntity() = ProductEntity(
    id = id,
    name = name,
    price = price,
    categoryId = category.id,
    image = image,
    description = description
)

// TODO: handle category name
fun ProductEntity.toProduct() = Product(
    id = id,
    name = name,
    price = price,
    category = Category(id = categoryId, name = ""),
    image = image,
    description = description
)
