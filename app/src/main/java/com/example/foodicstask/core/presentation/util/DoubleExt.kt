package com.example.foodicstask.core.presentation.util

import java.text.DecimalFormat

/**
 * @return Double with two decimal places.
 */
fun Double.formatToTwoDecimalPlaces(): Double {
    val df = DecimalFormat("#.##")
    return df.format(this).toDouble()
}
