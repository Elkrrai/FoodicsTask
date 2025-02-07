package com.example.foodicstask.tables.data.source.mappers

import com.example.foodicstask.tables.data.source.remote.dto.CategoryDto
import com.example.foodicstask.tables.data.source.remote.dto.ProductDto
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

fun CategoryDto.toCategory() =
    Category(
        id = id,
        name = name
    )

fun ProductDto.toProduct() =
    Product(
        id = id,
        category = category.toCategory(),
        name = name,
        description = description,
        image = image,
        price = price
    )
