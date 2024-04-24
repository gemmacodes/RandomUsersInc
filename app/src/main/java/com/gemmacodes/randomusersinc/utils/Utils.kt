package com.gemmacodes.randomusersinc.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date: Date = inputFormat.parse(this)!!
    return outputFormat.format(date)
}