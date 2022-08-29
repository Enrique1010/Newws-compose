package com.erapps.newws_compose.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

const val INCOMING_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun Date.isToday() = DateUtils.isToday(this.time)

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat(INCOMING_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(Date())
}

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat(INCOMING_DATE_FORMAT, Locale.getDefault())
    return dateFormat.parse(this)
}