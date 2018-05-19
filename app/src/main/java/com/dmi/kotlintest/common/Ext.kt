package com.dmi.kotlintest.common

import java.text.SimpleDateFormat
import java.util.*

fun Boolean?.orFalse() = this ?: false

fun String.clearPhone(): String {
    return this.replace(Regex("""\D"""), "")
}

fun Int?.orZero() = this ?: 0

fun String.toDate(format: DateFormats) : Date = format.formatter.parse(this)

fun Date?.toString(format: DateFormats): String {
    return when(this) {
        null -> ""
        else -> format.formatter.format(this)
    }
}

enum class DateFormats(val pattern: String) {
    SERVER("yyyy-MM-dd HH:mm:ss"),
    VISUAL("dd.MM.yyyy, HH:mm")
}

val DateFormats.formatter: SimpleDateFormat
    get() = SimpleDateFormat(this.pattern, Locale.getDefault())


