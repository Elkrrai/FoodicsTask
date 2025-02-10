package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product

/**
 * [GetProductsUseCase] is responsible for retrieving a list of [Product]
 * objects associated with a given [Category].
 *
 * This use case attempts to fetch products from a remote data source. If the remote fetch is
 * successful, it stores the products locally and updates the category-product relationship.
 * If the remote fetch fails, it attempts to retrieve products from the local data source.
 *
 * @property repository The repository responsible for fetching and storing product data.
 */
class GetProductsUseCase(
    private val repository: TablesRepository
) {
    /**
     * Fetches a list of [Product] objects for a given category ID.
     *
     * This function first attempts to fetch products from a remote source using the provided [category].
     * - If the remote fetch is successful, it inserts the fetched products into the local data source and returns the remote products.
     * - If the remote fetch fails, it attempts to fetch products from the local data source.
     * - If the local fetch is successful and the local data source contains products, it returns the local products.
     * - If the local fetch is successful but the local data source is empty, it returns the error from the remote fetch.
     * - If the local fetch fails, it returns a [DataError.LocalError].
     *
     * @param category The ID of the category for which to fetch products.
     * @return A [Result] object that encapsulates either:
     *   - A [Result.Success] containing a [List] of [Product] objects if the fetch is successful.
     *   - A [Result.Error] containing a [DataError] if the fetch fails.
     */
    suspend operator fun invoke(category: Category): Result<List<Product>, DataError> {
        val remoteProducts = repository.fetchProducts(category.id)
        return if (remoteProducts is Result.Error) {
            val localProducts = repository.getLocalProducts(category.id)
            if (localProducts is Result.Success && localProducts.data.isNotEmpty()) {
                Result.Success(localProducts.data)
            } else {
                if (localProducts is Result.Success) {
                    Result.Error(remoteProducts.error)
                } else {
                    Result.Error(DataError.LocalError)
                }
            }
        } else {
            val products = (remoteProducts as Result.Success).data
            repository.insertProducts(products)
            val productsIds = products.map { it.id }
            if (productsIds.isNotEmpty()) {
                repository.upsertProductsAndCategoryRelation(category, productsIds)
            }
            remoteProducts
        }
    }
}
