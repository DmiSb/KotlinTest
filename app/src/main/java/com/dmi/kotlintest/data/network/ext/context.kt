package com.dmi.kotlintest.data.network.ext

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean? {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}