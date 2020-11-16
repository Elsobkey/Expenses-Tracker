package com.sobky.expensestracking.ui.expenseitem

import kotlin.math.roundToInt

object Conv {

    @JvmStatic
    fun roundToInt(doubleVal: Double): Int {
        return doubleVal.roundToInt()
    }
}