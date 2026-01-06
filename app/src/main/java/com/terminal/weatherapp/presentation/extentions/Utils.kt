package com.terminal.weatherapp.presentation.extentions

import kotlin.math.roundToInt

fun Float.tempToFormattedString():String = "${roundToInt()}C"