package com.dmi.kotlintest.data.network.core

class HttpConfig {

    companion object {
        const val BASE_URL = "http://dev-exam.l-tech.ru/"
        const val MAX_CONNECTION_TIMEOUT = 10000L
        const val MAX_READ_TIMEOUT = 10000L
        const val MAX_WRITE_TIMEOUT = 10000L
    }
}