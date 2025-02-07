package com.example.foodicstask.di

import com.example.foodicstask.core.data.networking.HttpClientFactory
import com.example.foodicstask.food.data.source.remote.RemoteFoodDataSource
import com.example.foodicstask.food.domain.FoodDataSource
import com.example.foodicstask.food.domain.usecases.FetchCategoriesUseCase
import com.example.foodicstask.food.domain.usecases.FetchProductsUseCase
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteFoodDataSource).bind<FoodDataSource>()
    factory { FetchCategoriesUseCase(get()) }
    factory { FetchProductsUseCase(get()) }
}
