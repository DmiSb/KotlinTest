package com.dmi.kotlintest

import android.app.Application
import com.dmi.kotlintest.di.dependencies
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.lazy

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(dependencies())
    }
}