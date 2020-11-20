package com.themoviedb.utils

/**
 * @author- Nitin Khanna
 * @date -
 */
object StringUtils {
    fun getCurrency(value: Float, countryCode: String = "USD"): String {
        val amount = String.format("%.2f", value / 10_00_000)
        return when (countryCode) {
            "INR" -> "${amount}M ₹"
            else -> "${amount}M $"
        }
    }
}