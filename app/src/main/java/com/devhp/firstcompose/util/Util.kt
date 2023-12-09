package com.devhp.firstcompose.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun convertDate(date: Date): String {
    val formatter = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
    return formatter.format(date)
}