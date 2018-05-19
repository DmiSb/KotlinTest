package com.dmi.kotlintest.data.network.core

import com.dmi.kotlintest.common.DateFormats
import com.dmi.kotlintest.common.toDate
import com.dmi.kotlintest.common.toString
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateAdapter {
    @FromJson
    fun dateFromJson(value: String) = value.toDate(DateFormats.SERVER)

    @ToJson
    fun dateToJson(value: Date) = value.toString(DateFormats.VISUAL)
}