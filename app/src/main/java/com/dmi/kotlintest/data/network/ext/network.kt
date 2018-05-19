package com.dmi.kotlintest.data.network.ext

import android.content.Context
import com.dmi.kotlintest.R
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instanceOrNull
import io.reactivex.Single

fun <T> Single<T>.mapToNetworkException(injector: KodeinInjector): Single<T> {
    val context: Context? by injector.instanceOrNull()
    val networkAvailable = context?.isNetworkAvailable() ?: false
    return if (networkAvailable) this
    else Single.error(Throwable(context?.getString(R.string.err_network_is_not_available)))
}

fun <T> Single<T>.mapToException(): Single<T> {
    return this.onErrorResumeNext {
        throwable -> io.reactivex.Single.error(Throwable(throwable.message))
    }
}