package com.soft.centralcurrency.data.api

import com.soft.centralcurrency.data.dto.CurrencyDescription
import com.soft.centralcurrency.data.dto.CurrencyLatest
import io.reactivex.Observable
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest")
    fun getCurrencies(): Observable<CurrencyLatest>

    @GET("currencies")
    fun getDescription(): Observable<CurrencyDescription>

}