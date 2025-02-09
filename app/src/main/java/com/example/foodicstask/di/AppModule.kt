package com.example.foodicstask.di

import com.example.foodicstask.core.data.networking.HttpClientFactory
import com.example.foodicstask.tables.data.source.remote.RemoteFoodDataSource
import com.example.foodicstask.tables.domain.FoodDataSource
import com.example.foodicstask.tables.domain.usecases.FetchCategoriesUseCase
import com.example.foodicstask.tables.domain.usecases.FetchProductsUseCase
import com.example.foodicstask.tables.presentation.TablesViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteFoodDataSource).bind<FoodDataSource>()
    factory { FetchCategoriesUseCase(get()) }
    factory { FetchProductsUseCase(get()) }
    viewModel { TablesViewModel(get(), get()) }
}
