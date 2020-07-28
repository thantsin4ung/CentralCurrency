package com.soft.centralcurrency.data.dto

import org.joda.time.DateTime

data class ExchangeData(
    val updatedTime: DateTime,
    val rates: List<ExchangeRates>
)