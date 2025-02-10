package com.example.foodicstask.tables.presentation.mappers

import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import com.example.foodicstask.tables.presentation.models.CategoryUi
import com.example.foodicstask.tables.presentation.models.ProductUi

fun Category.toUiModel() =
    CategoryUi(
        id = id,
        name = name
    )

fun Product.toUiModel() =
    ProductUi(
        id = id,
        name = name,
        description = description,
        image = image,
        price = price,
        category = category.toUiModel(),
        ordered = 0
    )

fun CategoryUi.toCategory() =
    Category(
        id = id,
        name = name
    )
