package com.dmi.kotlintest.data.network.core

import com.dmi.kotlintest.common.orFalse
import com.dmi.kotlintest.data.model.DataInfo
import com.dmi.kotlintest.data.model.PhoneMask
import com.dmi.kotlintest.data.network.ext.mapToException
import com.github.salomonbrys.kodein.KodeinInjector
import io.reactivex.Single

class HttpService(
        val client: HttpClient,
        val injector: KodeinInjector
) : Service {

    private val service = client.createService(RestService::class.java)

    fun getPhoneMask(): Single<PhoneMask> {
        val single = service.getPhoneMask()
        return client.compose(single, injector)
                .mapToException()
                .map { response -> response.body() }
    }

    fun auth(phone: String, password: String): Single<out Boolean> {
        val single = service.auth(phone, password)
        return client.compose(single, injector)
                .mapToException()
                .map { response ->
                    when {
                        response.code() == 200 -> response.body()?.success.orFalse()
                        else -> false
                    }
                }
    }

    fun getEndpoints(): Single<List<DataInfo>> {
        val single = service.getEndpoints()
        return client.compose(single, injector)
                .mapToException()
                .map { response -> response.body() }
    }
}