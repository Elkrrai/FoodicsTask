package com.example.foodicstask.core.data.networking

private const val BASE_URL = "https://my.api.mockaroo.com"

object Routes {
    fun getCategoriesRoute() = "$BASE_URL/categories.json"
    fun getProductsRoute(categoryId: Int) = "$BASE_URL/products/$categoryId.json"
}
