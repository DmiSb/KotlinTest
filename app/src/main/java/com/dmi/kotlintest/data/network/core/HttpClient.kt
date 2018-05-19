package com.dmi.kotlintest.data.network.core

import com.dmi.kotlintest.data.network.ext.mapToNetworkException
import com.github.salomonbrys.kodein.KodeinInjector
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class HttpClient(
        private val backgroundScheduler: Scheduler = Schedulers.io(),
        private val resultScheduler: Scheduler = AndroidSchedulers.mainThread()
) {
    private val httpClient by lazy { createHttpClientInstance() }

    private fun createHttpClientInstance() : OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(HttpConfig.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpConfig.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpConfig.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()
    }

    private fun createRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(createConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
    }

    private fun createConverterFactory() : Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder()
                .add(DateAdapter())
                .build())
    }

    fun <S> createService(serviceClass: Class<S>) : S {
        val retrofit = createRetrofitInstance()
        return retrofit.create(serviceClass)
    }

    fun <T> compose(single: Single<out T>, injector: KodeinInjector) : Single<out T> {
        return single
                .subscribeOn(backgroundScheduler)
                .observeOn(resultScheduler)
                .mapToNetworkException(injector)
    }
}

