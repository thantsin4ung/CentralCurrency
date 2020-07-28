package com.soft.centralcurrency.data.service

import com.soft.centralcurrency.data.api.CurrencyApi
import com.soft.centralcurrency.data.dto.ExchangeData
import com.soft.centralcurrency.data.dto.ExchangeRates
import com.soft.centralcurrency.data.dto.Rates
import io.reactivex.Observable
import org.joda.time.DateTime

class CurrencyService(private val api: CurrencyApi) {

    fun getCurrencyRate(): Observable<ExchangeData> {
        return api.getCurrencies().map {
            val dateTime = DateTime(it.timestamp.toLong() * 1000)

            val rates = mutableListOf<ExchangeRates>()

            val ratesImportant = mutableListOf<ExchangeRates>()

            val desc = api.getDescription().blockingFirst()

            for (f in Rates::class.java.declaredFields) {
                f.isAccessible = true
                val rate = ExchangeRates(f.name, f.get(desc.currencies) as String, f.get(it.rates) as String)
                when(f.name) {
                    in "USD", "EUR", "SGD", "THB", "MYR", "GBP" -> {
                        ratesImportant.add(rate)
                    } else -> rates.add(rate)
                }
            }
            ratesImportant.addAll(rates)
            return@map ExchangeData(dateTime, ratesImportant)
        }
    }

}