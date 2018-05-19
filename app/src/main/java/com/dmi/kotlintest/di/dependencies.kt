package com.dmi.kotlintest.di

import com.dmi.kotlintest.data.network.core.HttpClient
import com.dmi.kotlintest.data.network.core.HttpService
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

fun dependencies() = Kodein.Module {
    bind<HttpClient>() with singleton {
        HttpClient()
    }

    bind<HttpService>() with singleton {
        HttpService(
                client = kodein.instance(),
                injector = kodein.instance()
        )
    }
}
