package com.example.foodicstask.di

import com.example.foodicstask.core.data.networking.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}
