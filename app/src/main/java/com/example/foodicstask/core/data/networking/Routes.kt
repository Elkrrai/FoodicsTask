package com.example.foodicstask.core.data.networking

private const val BASE_URL = "https://my.api.mockaroo.com"

/**
 * Object containing the API routes for the application.
 *
 * This object provides functions to generate the full URL for different API endpoints.
 */
object Routes {
    /**
     * Generates the URL for fetching categories.
     *
     * @return The full URL for the categories endpoint.
     */
    fun getCategoriesRoute() = "$BASE_URL/categories.json"

    /**
     * Generates the URL for fetching products for a specific category.
     *
     * @param categoryId The ID of the category for which to fetch products.
     * @return The full URL for the products endpoint for the given category ID.
     */
    fun getProductsRoute(categoryId: Int) = "$BASE_URL/products/$categoryId.json"
}
