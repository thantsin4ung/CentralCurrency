package com.soft.centralcurrency.data.dto

import java.text.DecimalFormat

data class ExchangeRates(
    val shortName: String,
    val desc: String,
    val rates: String
) {

    val _rate: String
        get() = when (shortName) {
            in "JPY", "KHR", "IDR", "KRW", "LAK", "VND" -> {
                val df = DecimalFormat("#,###.###")
                df.format(rates.replace(",", "").toDouble().div(100))
            }
            else ->
                rates
        }
}

