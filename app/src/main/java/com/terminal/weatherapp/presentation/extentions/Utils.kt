package com.terminal.weatherapp.presentation.extentions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

fun Float.tempToFormattedString(): String = "${roundToInt()}C"


@SuppressLint("SimpleDateFormat")
fun Calendar.formattedFullDate(): String {
    val format = SimpleDateFormat("EEEE | d MMM y", Locale.getDefault())
    return format.format(time)
}



@SuppressLint("SimpleDateFormat")
fun Calendar.formattedShortDate(): String {
    val format = SimpleDateFormat("EEE", Locale.getDefault())
    return format.format(time)
}
