package com.example.foodicstask.core.presentation.util

/**
 * Formats the number of orders to a two-digit string.
 */
fun Int.formatOrders(): String {
    return if (this > 9)
        this.toString()
    else "0$this"
}