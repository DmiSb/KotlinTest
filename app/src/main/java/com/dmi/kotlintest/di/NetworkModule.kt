package com.dmi.kotlintest.di

import com.dmi.kotlintest.data.network.core.HttpService
import com.github.salomonbrys.kodein.*

val networkModule = Kodein.Module {

    bind<HttpService>() with singleton {
        HttpService(
                client = kodein.instance(),
                injector = kodein.instance()
        )
    }
}
