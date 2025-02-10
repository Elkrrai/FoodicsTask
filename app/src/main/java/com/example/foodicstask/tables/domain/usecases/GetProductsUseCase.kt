package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Product

class GetProductsUseCase(
    private val repository: TablesRepository
) {
    suspend operator fun invoke(categoryId: Int): Result<List<Product>, NetworkError> {
        return repository
            .fetchProducts(categoryId)
            .onError {
                // TODO: get products from local db
                return Result.Error(it)
            }
    }
}
