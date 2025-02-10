package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category

class GetCategoriesUseCase(
    private val repository: TablesRepository
) {
    suspend operator fun invoke(): Result<List<Category>, DataError> {
        val remoteCategories = repository.fetchCategories()

        return if (remoteCategories is Result.Error) {
            val localCategories = repository.getLocalCategories()
            if (localCategories is Result.Success && localCategories.data.isNotEmpty()) {
                Result.Success(localCategories.data)
            } else {
                if (localCategories is Result.Success) {
                    Result.Error(remoteCategories.error)
                } else {
                    Result.Error(DataError.LocalError)
                }
            }
        } else {
            repository.insertCategories((remoteCategories as Result.Success).data)
            remoteCategories
        }
    }
}
