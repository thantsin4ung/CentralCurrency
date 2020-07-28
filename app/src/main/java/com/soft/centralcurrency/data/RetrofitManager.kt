package com.soft.centralcurrency.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitManager {

    private val retrofit: Retrofit

    private const val BASE_URL = "https://forex.cbm.gov.mm/api/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun <T> create(clazz: Class<T>) = retrofit.create(clazz)

}