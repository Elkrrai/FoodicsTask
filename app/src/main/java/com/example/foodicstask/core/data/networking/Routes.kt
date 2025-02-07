package com.example.foodicstask.core.data.networking

private const val BASE_URL = "https://my.api.mockaroo.com"
private const val API_KEY = "1f9896f0"  // this should be hidden in a secret file

object Routes {
    fun getCategoriesRoute() = "$BASE_URL/categories.json?key=$API_KEY"
    fun getProductsRoute(categoryId: Int) = "$BASE_URL/products/$categoryId.json?key=$API_KEY"
}
