package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Product

class GetProductsUseCase(
    private val repository: TablesRepository
) {
    suspend operator fun invoke(categoryId: Int): Result<List<Product>, DataError> {
        val remoteProducts = repository.fetchProducts(categoryId)
        return if (remoteProducts is Result.Error) {
            val localProducts = repository.getLocalProducts()
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
            repository.insertProducts((remoteProducts as Result.Success).data)
            remoteProducts
        }
    }
}
