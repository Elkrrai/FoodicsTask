package com.example.foodicstask.tables.domain.usecases

import com.example.foodicstask.core.domain.util.DataError
import com.example.foodicstask.core.domain.util.Result
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.entities.Category

/**
 * [GetCategoriesUseCase] is a use case class responsible for fetching a list of [Category] objects.
 * It prioritizes fetching data from a remote source but falls back to a local data source if the remote fetch fails.
 *
 * @property repository The [TablesRepository] instance used to interact with both remote and local data sources.
 */
class GetCategoriesUseCase(
    private val repository: TablesRepository
) {
    /**
     * Fetches a list of [Category] objects.
     *
     * This function first attempts to fetch categories from a remote source.
     * - If the remote fetch is successful, it inserts the fetched categories into the local data source and returns the remote categories.
     * - If the remote fetch fails, it attempts to fetch categories from the local data source.
     *   - If the local fetch is successful and the local data source contains categories, it returns the local categories.
     *   - If the local fetch is successful but the local data source is empty, it returns the error from the remote fetch.
     *   - If the local fetch fails, it returns a [DataError.LocalError].
     *
     * @return A [Result] object that encapsulates either:
     *   - A [Result.Success] containing a [List] of [Category] objects if the fetch is successful.
     *   - A [Result.Error] containing a [DataError] if the fetch fails.
     */
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
