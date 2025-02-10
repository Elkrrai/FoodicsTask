package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError.NetworkError
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.core.domain.util.onError
import com.example.foodicstask.tables.domain.TablesRepository

class GetCategoriesUseCase(
    private val repository: TablesRepository
) {
    suspend operator fun invoke(): Result<List<Category>, NetworkError> {
        return repository
            .fetchCategories()
            .onError {
                // TODO: get categories from local db
                return Result.Error(it)
            }
    }
}
