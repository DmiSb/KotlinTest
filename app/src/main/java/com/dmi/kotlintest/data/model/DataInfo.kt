package com.dmi.kotlintest.data.model

import java.io.Serializable
import java.util.*

data class DataInfo(
        val id: Int?,
        val title: String?,
        val text: String?,
        val image: String?,
        val sort: Int?,
        val date: Date?
) : Serializable