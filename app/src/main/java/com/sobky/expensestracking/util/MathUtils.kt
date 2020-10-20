package com.sobky.expensestracking.util

import kotlin.math.pow
import kotlin.math.roundToInt

//@Deprecated("This code is not thread-safe. Use ControlledRunner below instead.", level = ERROR)
fun roundNumber(value: Double, places: Int): Double {
    var valuee = value
    require(places >= 0)
    val factor = 10.0.pow(places.toDouble()).toLong()
    valuee *= factor
    val tmp = value.roundToInt() //round
    return tmp.toDouble() / factor
}