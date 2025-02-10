package com.example.foodicstask.di

import com.example.foodicstask.core.data.networking.HttpClientFactory
import com.example.foodicstask.tables.data.source.TablesRepositoryImpl
import com.example.foodicstask.tables.data.source.local.LocalTablesDataSource
import com.example.foodicstask.tables.data.source.local.database.TablesDatabase
import com.example.foodicstask.tables.data.source.remote.RemoteTablesDataSource
import com.example.foodicstask.tables.domain.TablesRepository
import com.example.foodicstask.tables.domain.usecases.GetCategoriesUseCase
import com.example.foodicstask.tables.domain.usecases.GetProductsUseCase
import com.example.foodicstask.tables.presentation.TablesViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteTablesDataSource)
    single { TablesDatabase.getInstance(androidContext()) }
    singleOf(::TablesRepositoryImpl).bind<TablesRepository>()
    singleOf(::LocalTablesDataSource)
    factory { GetCategoriesUseCase(get()) }
    factory { GetProductsUseCase(get()) }
    viewModel { TablesViewModel(get(), get()) }
}
